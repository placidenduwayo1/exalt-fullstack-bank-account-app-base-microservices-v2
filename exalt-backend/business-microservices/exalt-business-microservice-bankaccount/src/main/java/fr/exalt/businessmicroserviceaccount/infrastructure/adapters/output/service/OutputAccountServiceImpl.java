package fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.service;

import fr.exalt.businessmicroserviceaccount.domain.avrobeans.CurrentBankAccountAvro;
import fr.exalt.businessmicroserviceaccount.domain.avrobeans.SavingBankAccountAvro;
import fr.exalt.businessmicroserviceaccount.domain.entities.BankAccount;
import fr.exalt.businessmicroserviceaccount.domain.entities.CurrentBankAccount;
import fr.exalt.businessmicroserviceaccount.domain.entities.Customer;
import fr.exalt.businessmicroserviceaccount.domain.entities.SavingBankAccount;
import fr.exalt.businessmicroserviceaccount.domain.exceptions.BankAccountNotFoundException;
import fr.exalt.businessmicroserviceaccount.domain.finalvalues.FinalValues;
import fr.exalt.businessmicroserviceaccount.domain.ports.output.OutputAccountService;
import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.input.feignclient.models.CustomerModel;
import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.input.feignclient.proxy.RemoteCustomerServiceProxy;
import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.mapper.MapperService;
import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.models.entities.BankAccountModel;
import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.models.entities.CurrentBankAccountModel;
import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.models.entities.SavingBankAccountModel;
import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.repository.BankAccountRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class OutputAccountServiceImpl implements OutputAccountService {
    private final BankAccountRepository bankAccountRepository;
    private final RemoteCustomerServiceProxy remoteCustomerService;
    private final Logger logger = Logger.getLogger(this.getClass().getSimpleName());

    public OutputAccountServiceImpl(
            BankAccountRepository bankAccountRepository,
            @Qualifier(value = "remoteCustomerService") RemoteCustomerServiceProxy remoteCustomerService) {
        this.bankAccountRepository = bankAccountRepository;
        this.remoteCustomerService = remoteCustomerService;
    }

    @Override
    @KafkaListener(
            groupId = "group1", autoStartup = "true", topicPartitions = @TopicPartition(
            topic = "current-bank-account",
            partitionOffsets = @PartitionOffset(partition = "0", initialOffset = "0")))
    public BankAccount createCurrentAccountKafkaConsumer(@Payload CurrentBankAccountAvro avro) {
        /*read current account avro from kafka topic partition 0 and register in db*/
        logger.log(Level.INFO, "bank account avro to create in db {0}", avro);
        CurrentBankAccountModel model = MapperService.mapToCurrentAccountModel(avro);
        logger.log(Level.INFO, "current bank account model {0}", model);
        return MapperService.mapToCurrentAccount(bankAccountRepository.save(model));
    }

    @Override
    @KafkaListener(groupId = "group1",autoStartup = "true",
    topicPartitions = @TopicPartition(topic = "saving-bank-account",partitionOffsets = @PartitionOffset(partition = "0",initialOffset = "0")))
    public BankAccount createSavingAccountKafkaConsumer(SavingBankAccountAvro avro) {
       logger.log(Level.INFO, "bank account saving in db {0}", avro);
       SavingBankAccountModel model = MapperService.mapToSavingAccountModel(avro);
       logger.log(Level.INFO, "saving bank account model {0}", model);
       return MapperService.mapToSavingAccount(bankAccountRepository.save(model));
    }

    @Override
    public Collection<BankAccount> getAllAccounts() {
        return mapBankAccounts(bankAccountRepository.findAllAccounts());
    }

    @Override
    public BankAccount getAccount(String accountId) {
        BankAccountModel model = bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new BankAccountNotFoundException(FinalValues.BANK_ACCOUNT_NOT_FOUND));
        return mapBankAccount(model);
    }

    @Override
    public Collection<BankAccount> getAccountOfGivenCustomer(String customerId) {
        return mapBankAccounts(bankAccountRepository.findByCustomerId(customerId));
    }


    @Override
    public Customer loadRemoteCustomer(String customerId) {
        CustomerModel model = remoteCustomerService.loadRemoteCustomer(customerId);
        return MapperService.fromTo(model);
    }

    @Override
    public CurrentBankAccount updateCurrentAccount(CurrentBankAccount currentAccount) {
        CurrentBankAccountModel updatedModel = bankAccountRepository
                .save(MapperService.mapToCurrentAccountModel(currentAccount));
        return MapperService.mapToCurrentAccount(updatedModel);
    }

    @Override
    public SavingBankAccount updateSavingAccount(SavingBankAccount savingBankAccount) {
        SavingBankAccountModel model = bankAccountRepository
                .save(MapperService.mapToSavingAccountModel(savingBankAccount));
        return MapperService.mapToSavingAccount(model);
    }

    @Override
    public BankAccount switchAccountStateKafkaConsumer(BankAccount bankAccount) {
        switch (bankAccount){
            case CurrentBankAccount current ->{
                CurrentBankAccountModel model = MapperService.mapToCurrentAccountModel(current);
                CurrentBankAccountModel savedAccount = bankAccountRepository.save(model);
                return MapperService.mapToCurrentAccount(savedAccount);
            }
            case SavingBankAccount saving ->{
                SavingBankAccountModel model = MapperService.mapToSavingAccountModel(saving);
                SavingBankAccountModel savedAccount = bankAccountRepository.save(model);
                return MapperService.mapToSavingAccount(savedAccount);
            }
            default -> {
                return null;
            }
        }
    }

    @Override
    @KafkaListener(groupId = "group1", autoStartup = "true",
            topicPartitions = @TopicPartition(topic = "current-bank-account",
                    partitionOffsets = @PartitionOffset(partition = "1", initialOffset = "0")))
    public BankAccount changeOverdraftKafkaConsumer(CurrentBankAccountAvro avro) {
        logger.log(Level.INFO, "current bank account to change overdraft {0}", avro);
        CurrentBankAccountModel model = bankAccountRepository
                .save(MapperService.mapToCurrentAccountModel(avro));
        return MapperService.mapToCurrentAccount(model);
    }

    @Override
    @KafkaListener(groupId = "group1", autoStartup = "true",
            topicPartitions = @TopicPartition(topic = "saving-bank-account",
                    partitionOffsets = @PartitionOffset(partition = "1", initialOffset = "0")))
    public BankAccount changeInterestRateKafkaConsumer(SavingBankAccountAvro avro) {
        logger.log(Level.INFO, "saving bank account to change interest rate {0}", avro);
        SavingBankAccountModel model = bankAccountRepository
                .save(MapperService.mapToSavingAccountModel(avro));
        return MapperService.mapToSavingAccount(model);
    }

    @Override
    public Collection<BankAccount> getAllSuspendedAccounts() {
        return mapBankAccounts(bankAccountRepository.findAllDeactivate());
    }

    @Override
    public Collection<BankAccount> getAllRiskAccounts() {
        return mapBankAccounts(bankAccountRepository.findAllRiskAccounts());
    }


    private Collection<BankAccount> mapBankAccounts(Collection<BankAccountModel> bankAccountModels) {
        return bankAccountModels.stream().map(model -> {
            BankAccount bankAccount = null;
            switch (model){
                case CurrentBankAccountModel current ->
                    bankAccount = MapperService.mapToCurrentAccount(current);
                case SavingBankAccountModel saving ->
                        bankAccount = MapperService.mapToSavingAccount(saving);
                default -> logger.log(Level.INFO,"{0}",model);
            }
            return bankAccount;
        }).toList();
    }

    private BankAccount mapBankAccount(BankAccountModel model) {
        switch (model){
            case CurrentBankAccountModel current->{
                return MapperService.mapToCurrentAccount(current);
            }
            case SavingBankAccountModel saving ->{
                return MapperService.mapToSavingAccount(saving);
            }
            default -> {
                return null;
            }
        }
    }
}
