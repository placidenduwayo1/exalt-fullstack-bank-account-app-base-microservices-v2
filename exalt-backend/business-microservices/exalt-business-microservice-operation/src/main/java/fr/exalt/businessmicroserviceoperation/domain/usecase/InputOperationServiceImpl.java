package fr.exalt.businessmicroserviceoperation.domain.usecase;

import fr.exalt.businessmicroserviceoperation.domain.avromodels.transfer.DestinationAccountAvro;
import fr.exalt.businessmicroserviceoperation.domain.avromodels.transfer.OriginAccountAvro;
import fr.exalt.businessmicroserviceoperation.domain.avromodels.transfer.TransferOperationAvro;
import fr.exalt.businessmicroserviceoperation.domain.entities.BankAccount;
import fr.exalt.businessmicroserviceoperation.domain.entities.Customer;
import fr.exalt.businessmicroserviceoperation.domain.entities.Operation;
import fr.exalt.businessmicroserviceoperation.domain.entities.TransferOperation;
import fr.exalt.businessmicroserviceoperation.domain.exceptions.*;
import fr.exalt.businessmicroserviceoperation.domain.finalvalues.FinalValues;
import fr.exalt.businessmicroserviceoperation.domain.ports.input.InputOperationService;
import fr.exalt.businessmicroserviceoperation.domain.ports.output.KafkaProducerService;
import fr.exalt.businessmicroserviceoperation.domain.ports.output.OutputOperationService;
import fr.exalt.businessmicroserviceoperation.infrastructure.adapters.input.feignclient.models.BankAccountDto;
import fr.exalt.businessmicroserviceoperation.infrastructure.adapters.output.mapper.MapperService;
import fr.exalt.businessmicroserviceoperation.infrastructure.adapters.output.models.dtos.OperationDto;
import fr.exalt.businessmicroserviceoperation.infrastructure.adapters.output.models.dtos.TransferDto;

import java.time.Instant;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static fr.exalt.businessmicroserviceoperation.domain.finalvalues.FinalValues.*;

public class InputOperationServiceImpl implements InputOperationService {
    private final OutputOperationService outputOperationService;
    private final KafkaProducerService kafkaProducer;
    private final Logger logger = Logger.getLogger(this.getClass().getSimpleName());

    public InputOperationServiceImpl(OutputOperationService outputOperationService, KafkaProducerService kafkaProducer) {
        this.outputOperationService = outputOperationService;
        this.kafkaProducer = kafkaProducer;
    }

    private void validateOperation(OperationDto dto) throws OperationRequestFieldsInvalidException, OperationTypeInvalidException {
        OperationValidators.formatter(dto);
        if (OperationValidators.invalidOperationRequest(dto)) {
            throw new OperationRequestFieldsInvalidException(FinalValues.OPERATION_REQUEST_FIELDS);
        }
        if (!OperationValidators.invalidOperationType(dto.getType())) {
            throw new OperationTypeInvalidException(FinalValues.OPERATION_TYPE);
        }
    }

    @Override
    public Operation createOperation(OperationDto operationDto) {

        validateOperation(operationDto);
        BankAccount bankAccount = outputOperationService.loadRemoteAccount(operationDto.getAccountId());
        Customer customer = outputOperationService.loadRemoteCustomer(bankAccount.getCustomerId());
        if (bankAccount.getAccountId().equals(FinalValues.REMOTE_ACCOUNT_UNREACHABLE)) {
            throw new RemoteBankAccountApiUnreachableException(String.format(FORMATTER,
                    FinalValues.REMOTE_ACCOUNT_UNREACHABLE, bankAccount));
        } else if (bankAccount.getState().equals(BANK_ACCOUNT_STATE_SUSPEND)) {
            throw new RemoteBankAccountSuspendedException(FinalValues.REMOTE_ACCOUNT_SUSPENDED);
        }
        if (customer.getCustomerId().equals(FinalValues.REMOTE_CUSTOMER_UNREACHABLE)) {
            throw new RemoteCustomerApiUnreachableException(String.format(FORMATTER,
                    FinalValues.REMOTE_CUSTOMER_UNREACHABLE, customer));
        }

        if (customer.getState().equals(CUSTOMER_STATE_ARCHIVE)) {
            throw new RemoteCustomerStateInvalidException(FinalValues.REMOTE_CUSTOMER_STATE);
        }

        if (bankAccount.getType().equals(BANK_ACCOUNT_TYPE_SAVING)) {
            throw new RemoteBankAccountTypeInaccessibleFromOutsideException(
                    FinalValues.REMOTE_ACCOUNT_NOT_ACCESSIBLE_FROM_OUTSIDE);
        }

        //operation de retrait from the remote account api
        Operation operation = MapperService.fromTo(operationDto);

        if (operationDto.getType().equals(OPERATION_TYPE_RETRAIT) &&
                (!OperationValidators.enoughBalance(bankAccount, operationDto.getMount()))) {
            throw new RemoteBankAccountBalanceException(FinalValues.REMOTE_ACCOUNT_BALANCE);
        } else if (operationDto.getType().equals(OPERATION_TYPE_RETRAIT) &&
                (OperationValidators.enoughBalance(bankAccount, operationDto.getMount()))) {
            bankAccount.setBalance(-operationDto.getMount());
            BankAccountDto bankAccountDto = MapperService.fromTo(bankAccount);
            BankAccount updatedBankAccount = outputOperationService.updateRemoteAccount(bankAccount.getAccountId(), bankAccountDto);
            updatedBankAccount.setCustomer(customer);

            operation.setOperationId(UUID.randomUUID().toString());
            operation.setCreatedAt(Instant.now().toString());
            operation.setBankAccount(updatedBankAccount);
            // call kafka producer to send operation payload event to topic
            kafkaProducer.producerOperationAvroModelCreateEvent(MapperService.from(operation));
        }
        //operation de depot sur le remote account api
        else if (operationDto.getType().equals(OPERATION_TYPE_DEPOSIT)) {
            bankAccount.setBalance(operationDto.getMount());
            BankAccountDto bankAccountDto = MapperService.fromTo(bankAccount);
            BankAccount updatedBankAccount = outputOperationService.updateRemoteAccount(bankAccount.getAccountId(), bankAccountDto);
            updatedBankAccount.setCustomer(customer);

            operation.setOperationId(UUID.randomUUID().toString());
            operation.setCreatedAt(Instant.now().toString());
            operation.setBankAccount(updatedBankAccount);
            // call kafka producer to send operation payload event to topic
            kafkaProducer.producerOperationAvroModelCreateEvent(MapperService.from(operation));
        }

        return operation;
    }

    @Override
    public Collection<Operation> getAllOperations() {
        return setOperationDependencies(outputOperationService.getAllOperations());
    }

    @Override
    public Collection<Operation> getAccountOperations(String accountId){
        BankAccount account = outputOperationService.loadRemoteAccount(accountId);
        if (OperationValidators.remoteAccountApiUnreachable(account.getAccountId())) {
            throw new RemoteBankAccountApiUnreachableException(
                    String.format(FORMATTER, FinalValues.REMOTE_ACCOUNT_UNREACHABLE, account));
        } else if (account.getType().equals(BANK_ACCOUNT_TYPE_SAVING)) {
            throw new RemoteBankAccountTypeInaccessibleFromOutsideException(
                    FinalValues.REMOTE_ACCOUNT_NOT_ACCESSIBLE_FROM_OUTSIDE);
        }
        return setOperationDependencies(outputOperationService.getAccountOperations(accountId));
    }

    @Override
    public Map<String, BankAccount> transferBetweenAccounts(TransferDto dto) {
        // call output adapter to get remote bank accounts from bank account api
        BankAccount origin = outputOperationService.loadRemoteAccount(dto.getOrigin());
        BankAccount destination = outputOperationService.loadRemoteAccount(dto.getDestination());

        if (origin.getAccountId().equals(FinalValues.REMOTE_ACCOUNT_UNREACHABLE)
                || destination.getAccountId().equals(FinalValues.REMOTE_ACCOUNT_UNREACHABLE)) {
            throw new RemoteBankAccountApiUnreachableException(FinalValues.REMOTE_ACCOUNT_UNREACHABLE);
        }

        if (origin.getState().equals(BANK_ACCOUNT_STATE_SUSPEND) || destination.getState().equals(BANK_ACCOUNT_STATE_SUSPEND)) {
            throw new RemoteAccountSuspendedException(FinalValues.REMOTE_BANK_ACCOUNT_SUSPENDED);
        }
        // call output adapter to get remote customers from customer api
        Customer customer1 = outputOperationService.loadRemoteCustomer(origin.getCustomerId());
        Customer customer2 = outputOperationService.loadRemoteCustomer(destination.getCustomerId());
        if (customer1.getCustomerId().equals(FinalValues.REMOTE_CUSTOMER_UNREACHABLE)
                || customer2.getCustomerId().equals(FinalValues.REMOTE_CUSTOMER_UNREACHABLE)) {
            throw new RemoteCustomerApiUnreachableException(FinalValues.REMOTE_CUSTOMER_UNREACHABLE);
        }
        if (customer1.getState().equals(CUSTOMER_STATE_ARCHIVE)) {
            throw new RemoteCustomerStateInvalidException(FinalValues.REMOTE_CUSTOMER_STATE);
        }
        if (!OperationValidators.enoughBalance(origin, dto.getMount())) {
            throw new RemoteBankAccountBalanceException(FinalValues.REMOTE_ACCOUNT_BALANCE);
        }

        // money transfer between current and saving only possible for the same customer
        if ((origin.getType().equals(BANK_ACCOUNT_TYPE_SAVING) && !customer2.getCustomerId().equals(origin.getCustomerId()))
                || (destination.getType().equals(BANK_ACCOUNT_TYPE_SAVING) && !customer1.getCustomerId().equals(destination.getCustomerId()))) {
            throw new RemoteBankAccountTypeInaccessibleFromOutsideException(FinalValues.REMOTE_ACCOUNT_NOT_ACCESSIBLE_FROM_OUTSIDE);
        }

        origin.setBalance(-dto.getMount());
        BankAccountDto mappedOrigin = MapperService.fromTo(origin);
        // call output adapter to update origin account balance from remote bank account
        BankAccount updatedOrigin = outputOperationService.updateRemoteAccount(origin.getAccountId(), mappedOrigin);
        updatedOrigin.setCustomer(customer1);
        destination.setBalance(dto.getMount());
        BankAccountDto mappedDestination = MapperService.fromTo(destination);
        // call output adapter to update destination account balance from remote bank account
        BankAccount updatedDestination = outputOperationService.updateRemoteAccount(destination.getAccountId(), mappedDestination);
        updatedDestination.setCustomer(customer2);

        // call kafka producer to send in topic transfer the origin and destination bank accounts
        OriginAccountAvro originAccountAvro = MapperService.fromOrigin(updatedOrigin);
        DestinationAccountAvro destinationAccountAvro = MapperService.fromDestination(updatedDestination);
        TransferOperationAvro transferOperationAvro = TransferOperationAvro.newBuilder()
                .setOriginAccount(originAccountAvro)
                .setDestinationAccount(destinationAccountAvro)
                .setMount(dto.getMount())
                .build();
        logger.log(Level.INFO,"transfer model avro {0}", transferOperationAvro);
        kafkaProducer.producerTransferOperation(transferOperationAvro);
        return Map.of("origin", updatedOrigin, "destination", updatedDestination);

    }

    @Override
    public Collection<TransferOperation> getAllTransfer() {
        return outputOperationService.getAllTransfer().stream()
                .map(transfer -> {
                    BankAccount origin = outputOperationService.loadRemoteAccount(transfer.getOrigin());
                    Customer customerOrigin = outputOperationService.loadRemoteCustomer(origin.getCustomerId());
                    origin.setCustomer(customerOrigin);
                    BankAccount destination = outputOperationService.loadRemoteAccount(transfer.getDestination());
                    Customer customerDestination = outputOperationService.loadRemoteCustomer(destination.getCustomerId());
                    destination.setCustomer(customerDestination);
                    transfer.setAccountOrigin(origin);
                    transfer.setAccountDestination(destination);
                    return transfer;
                }).toList();

    }

    @Override
    public Collection<Operation> getAllDepositOperation() {
        return setOperationDependencies(outputOperationService.getAllDepositOperation());
    }

    @Override
    public Collection<Operation> getAllWithdrawalsOperation() {
        return setOperationDependencies(outputOperationService.getAllWithdrawalsOperation());
    }

    private Collection<Operation> setOperationDependencies(Collection<Operation> operations) {
        return operations.stream()
                .sorted((op1, op2) -> (int) (op2.getMount() - op1.getMount()))
                .map(operation -> {
                    BankAccount bankAccount = outputOperationService.loadRemoteAccount(operation.getAccountId());
                    Customer customer = outputOperationService.loadRemoteCustomer(bankAccount.getCustomerId());
                    bankAccount.setCustomer(customer);
                    operation.setBankAccount(bankAccount);
                    return operation;
                })
                .toList();
    }
}
