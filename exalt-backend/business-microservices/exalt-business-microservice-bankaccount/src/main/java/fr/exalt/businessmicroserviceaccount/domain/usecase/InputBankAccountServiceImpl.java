package fr.exalt.businessmicroserviceaccount.domain.usecase;

import fr.exalt.businessmicroserviceaccount.domain.entities.BankAccount;
import fr.exalt.businessmicroserviceaccount.domain.entities.CurrentBankAccount;
import fr.exalt.businessmicroserviceaccount.domain.entities.Customer;
import fr.exalt.businessmicroserviceaccount.domain.entities.SavingBankAccount;
import fr.exalt.businessmicroserviceaccount.domain.exceptions.*;
import fr.exalt.businessmicroserviceaccount.domain.finalvalues.FinalValues;
import fr.exalt.businessmicroserviceaccount.domain.ports.input.InputBankAccountService;
import fr.exalt.businessmicroserviceaccount.domain.ports.output.KafkaProducerService;
import fr.exalt.businessmicroserviceaccount.domain.ports.output.OutputAccountService;
import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.mapper.MapperService;
import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.models.dtos.BankAccountDto;
import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.models.dtos.BankAccountInterestRateDto;
import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.models.dtos.BankAccountOverdraftDto;
import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.models.dtos.BankAccountSwitchStatedDto;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collection;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import static fr.exalt.businessmicroserviceaccount.domain.finalvalues.FinalValues.*;

public class InputBankAccountServiceImpl implements InputBankAccountService {
    private final OutputAccountService outputAccountService;//output adapter
    private static final String FORMATTER = "%s,%n%s";
    private final KafkaProducerService kafkaProducerService;
    private final Logger logger = Logger.getLogger(this.getClass().getSimpleName());

    public InputBankAccountServiceImpl(OutputAccountService outputAccountService, KafkaProducerService kafkaProducer) {
        this.outputAccountService = outputAccountService;
        this.kafkaProducerService = kafkaProducer;
    }

    @Override
    public BankAccount createAccountKafkaEvent(BankAccountDto dto){
        validateAccount(dto);
        Customer customer = outputAccountService.loadRemoteCustomer(dto.getCustomerId());
        validateRemoteCustomer(customer.getCustomerId(), customer.getState());

        if (dto.getType().equals(CURRENT_ACCOUNT)) {
            CurrentBankAccount currentBankAccount = MapperService.mapToCurrentAccount(dto);
            currentBankAccount.setAccountId(UUID.randomUUID().toString());
            currentBankAccount.setCreatedAt(Timestamp.from(Instant.now()).toString());
            currentBankAccount.setState(ACTIVE_ACCOUNT);
            currentBankAccount.setOverdraft(INIT_OVERDRAFT);
            currentBankAccount.setType(CURRENT_ACCOUNT);
            currentBankAccount.setCustomer(customer);

            //call kafka producer to send current bank account event to create to kafka topic
            kafkaProducerService
                    .producerBankAccountModelCreateEvent(currentBankAccount);
            logger.log(Level.INFO, "current !!!!!!!!!!!!!!! {0}", currentBankAccount);
            return currentBankAccount;

        } else if (dto.getType().equals(SAVING_ACCOUNT)) {
            SavingBankAccount savingAccount = MapperService.mapToSavingAccount(dto);
            savingAccount.setAccountId(UUID.randomUUID().toString());
            savingAccount.setCreatedAt(Timestamp.from(Instant.now()).toString());
            savingAccount.setState(ACTIVE_ACCOUNT);
            savingAccount.setInterestRate(INIT_INTEREST);
            savingAccount.setType(SAVING_ACCOUNT);
            savingAccount.setCustomer(customer);
            //call kafka producer to send current bank account event to create to kafka topic
            kafkaProducerService
                    .producerBankAccountModelCreateEvent(savingAccount);
            logger.log(Level.INFO, "saving account !!!!!!!!!!!! {0}", savingAccount);
            return savingAccount;
        }

        return null;
    }

    @Override
    public Collection<BankAccount> getAllAccounts() {
        Collection<BankAccount> bankAccounts = outputAccountService.getAllAccounts();
        return setCustomerToAccount(bankAccounts);
    }

    @Override
    public BankAccount getAccount(String accountId){
        BankAccount bankAccount = outputAccountService.getAccount(accountId);
        if (bankAccount instanceof CurrentBankAccount current) {
            current.setType(CURRENT_ACCOUNT);
        } else if (bankAccount instanceof SavingBankAccount saving) {
            saving.setType(SAVING_ACCOUNT);
        }
        Customer customer = outputAccountService.loadRemoteCustomer(bankAccount.getCustomerId());
        bankAccount.setCustomer(customer);
        return bankAccount;
    }

    @Override
    public Collection<BankAccount> getAccountOfGivenCustomer(String customerId) {
        Collection<BankAccount> bankAccounts = outputAccountService.getAccountOfGivenCustomer(customerId);
        return setCustomerToAccount(bankAccounts);
    }

    @Override
    public BankAccount updateAccount(String accountId, BankAccountDto dto){

        Customer customer = outputAccountService.loadRemoteCustomer(dto.getCustomerId());
        validateRemoteCustomer(customer.getCustomerId(), customer.getState());
        validateAccount(dto);

        BankAccount bankAccount = getAccount(accountId);

        switch (bankAccount) {
            case SavingBankAccount saving->{
                SavingBankAccount savingBankAccount = SavingBankAccount.builder().build();
                //fields that will be not changed
                savingBankAccount.setAccountId(bankAccount.getAccountId());
                savingBankAccount.setState(bankAccount.getState());
                savingBankAccount.setCreatedAt(bankAccount.getCreatedAt());
                savingBankAccount.setInterestRate(saving.getInterestRate());

                //fields that will be changed
                savingBankAccount.setBalance(dto.getBalance() + bankAccount.getBalance());
                savingBankAccount.setCustomerId(dto.getCustomerId());

                // call to output adapter to save updated account in db
                SavingBankAccount updateSavingAccount = outputAccountService.updateSavingAccount(savingBankAccount);
                updateSavingAccount.setType(SAVING_ACCOUNT);
                updateSavingAccount.setCustomer(customer);
                return updateSavingAccount;
            }
            case CurrentBankAccount current -> {
                CurrentBankAccount currentAccount = CurrentBankAccount.builder().build();
                //fields that will be not changed
                currentAccount.setAccountId(bankAccount.getAccountId());
                currentAccount.setState(bankAccount.getState());
                currentAccount.setCreatedAt(bankAccount.getCreatedAt());
                currentAccount.setOverdraft(current.getOverdraft());

                //fields that will be changed
                currentAccount.setBalance(dto.getBalance() + bankAccount.getBalance());
                currentAccount.setCustomerId(dto.getCustomerId());
                // call to output adapter to save updated account in db
                CurrentBankAccount updateCurrentAccount = outputAccountService.updateCurrentAccount(currentAccount);
                updateCurrentAccount.setType(CURRENT_ACCOUNT);
                updateCurrentAccount.setCustomer(customer);
               return updateCurrentAccount;
            }
            default -> {
                return bankAccount;
            }
        }
    }

    @Override
    public BankAccount switchAccountState(BankAccountSwitchStatedDto dto) {

        BankAccount bankAccount = getAccount(dto.getAccountId());
        Customer customer = outputAccountService.loadRemoteCustomer(bankAccount.getCustomerId());
        validateRemoteCustomer(customer.getCustomerId(), customer.getState());
        if (!BankAccountValidators.validAccountState(dto.getState()))
            throw new BankAccountStateInvalidException(FinalValues.BANK_ACCOUNT_STATE_INVALID);

        else if (bankAccount.getState().equals(dto.getState()))
            throw new BankAccountSameStateException(FinalValues.BANK_ACCOUNT_SAME_STATE);
        else {
            bankAccount.setState(dto.getState());
            //call kafka producer to send bank account event to switch bank account state
            switch (bankAccount) {
                case SavingBankAccount saving -> kafkaProducerService.producerSavingAccountSwitchState(
                        MapperService.mapSavingAccountToAvro(saving));
                case CurrentBankAccount current -> kafkaProducerService.producerCurrentAccountSwitchState(
                        MapperService.mapCurrentAccountToAvro(current));
                default -> logger.log(Level.INFO, "{0}", bankAccount);
            }
            return bankAccount;
        }
    }

    @Override
    public BankAccount changeOverdraft(BankAccountOverdraftDto dto){

        BankAccount bankAccount = getAccount(dto.getAccountId());
        Customer customer = outputAccountService.loadRemoteCustomer(bankAccount.getCustomerId());
        validateRemoteCustomer(customer.getCustomerId(), customer.getState());
        if (bankAccount.getState().equals(SUSPEND_ACCOUNT)) {
            throw new BankAccountSuspendException(FinalValues.BANK_ACCOUNT_STATE_SUSPENDED);
        }

        if (bankAccount instanceof SavingBankAccount account) {
            throw new BankAccountTypeNotAcceptedException(String.format(FORMATTER,
                    FinalValues.BANK_ACCOUNT_TYPE_NOT_ACCEPTED, account));
        } else if (dto.getOverdraft() < 0) {
            throw new BankAccountOverdraftInvalidException(FinalValues.BANK_ACCOUNT_OVERDRAFT);
        } else {
            CurrentBankAccount currentBankAccount = new CurrentBankAccount.CurrentAccountBuilder()
                    .overdraft(dto.getOverdraft())
                    .build();
            //fields that value will not change
            currentBankAccount.setAccountId(bankAccount.getAccountId());
            currentBankAccount.setType(bankAccount.getType());
            currentBankAccount.setState(bankAccount.getState());
            currentBankAccount.setBalance(bankAccount.getBalance());
            currentBankAccount.setCreatedAt(bankAccount.getCreatedAt());
            currentBankAccount.setCustomerId(bankAccount.getCustomerId());
            currentBankAccount.setCustomer(customer);
            //call kafka producer to send current bank account event to change overdraft
            kafkaProducerService.producerCurrentAccountModelChangeOverdraft(
                    MapperService.mapCurrentAccountToAvro(currentBankAccount));
            return currentBankAccount;
        }

    }

    @Override
    public BankAccount changeInterestRate(BankAccountInterestRateDto dto){
        BankAccount bankAccount = getAccount(dto.getAccountId());
        Customer customer = outputAccountService.loadRemoteCustomer(bankAccount.getCustomerId());
        validateRemoteCustomer(customer.getCustomerId(), customer.getState());
        if (bankAccount.getState().equals(SUSPEND_ACCOUNT)) {
            throw new BankAccountSuspendException(FinalValues.BANK_ACCOUNT_STATE_SUSPENDED);
        }
        if (bankAccount instanceof CurrentBankAccount account) {
            throw new BankAccountTypeNotAcceptedException(String.format(FORMATTER,
                    FinalValues.BANK_ACCOUNT_TYPE_NOT_ACCEPTED, account));
        } else {
            SavingBankAccount savingAccount = new SavingBankAccount.SavingAccountBuilder()
                    .interestRate(dto.getInterestRate())
                    .build();
            // fields that value will not change
            savingAccount.setAccountId(bankAccount.getAccountId());
            savingAccount.setType(bankAccount.getType());
            savingAccount.setState(bankAccount.getState());
            savingAccount.setBalance(bankAccount.getBalance());
            savingAccount.setCreatedAt(bankAccount.getCreatedAt());
            savingAccount.setCustomerId(bankAccount.getCustomerId());
            savingAccount.setCustomer(bankAccount.getCustomer());
            //call kafka producer to send saving bank account event to change interest rate
            kafkaProducerService.producerSavingAccountModelChangeInterestRate(
                    MapperService.mapSavingAccountToAvro(savingAccount));
            return savingAccount;
        }
    }

    @Override
    public Collection<BankAccount> getAllSuspendedAccounts() {
        Collection<BankAccount> bankAccounts = outputAccountService.getAllSuspendedAccounts();
        return setCustomerToAccount(bankAccounts);
    }

    @Override
    public Collection<BankAccount> getAllRiskAccounts() {
        Collection<BankAccount> bankAccounts = outputAccountService.getAllRiskAccounts();
        return setCustomerToAccount(bankAccounts);
    }

    private void validateAccount(BankAccountDto dto) {
        BankAccountValidators.formatter(dto);

        if (BankAccountValidators.invalidFields(dto))
            throw new BankAccountFieldsInvalidException(FinalValues.BANK_ACCOUNT_FIELDS_INVALID);
        if (!BankAccountValidators.validAccountSType(dto.getType()))
            throw new BankAccountTypeInvalidException(FinalValues.BANK_ACCOUNT_TYPE_INVALID);
    }

    private void validateRemoteCustomer(String customerId, String customerState){
        if (BankAccountValidators.remoteCustomerApiUnreachable(customerId))
            throw new RemoteCustomerApiUnreachableException(FinalValues.REMOTE_CUSTOMER_API);
        else if (BankAccountValidators.remoteCustomerStateInvalid(customerState))
            throw new RemoteCustomerStateInvalidException(FinalValues.REMOTE_CUSTOMER_STATE);
    }

    private Collection<BankAccount> setCustomerToAccount(Collection<BankAccount> bankAccounts) {
        return bankAccounts.stream()
                .sorted((a1, a2) -> (int) (a2.getBalance() - a1.getBalance()))
                .map(account -> {
                    Customer remoteCustomer = outputAccountService.loadRemoteCustomer(account.getCustomerId());
                    account.setCustomer(remoteCustomer);
                    switch (account) {
                        case CurrentBankAccount current -> current.setType(CURRENT_ACCOUNT);
                        case SavingBankAccount saving -> saving.setType(SAVING_ACCOUNT);
                        default -> logger.log(Level.INFO, "{0}", account);
                    }
                    return account;
                })
                .toList();
    }
}
