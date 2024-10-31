package fr.exalt.businessmicroserviceoperation.infrastructure.adapters.output.mapper;

import fr.exalt.businessmicroserviceoperation.domain.avromodels.operation.BankAccountAvro;
import fr.exalt.businessmicroserviceoperation.domain.avromodels.operation.CustomerAvro;
import fr.exalt.businessmicroserviceoperation.domain.avromodels.operation.OperationAvro;
import fr.exalt.businessmicroserviceoperation.domain.avromodels.transfer.*;
import fr.exalt.businessmicroserviceoperation.domain.entities.BankAccount;
import fr.exalt.businessmicroserviceoperation.domain.entities.Customer;
import fr.exalt.businessmicroserviceoperation.domain.entities.Operation;
import fr.exalt.businessmicroserviceoperation.domain.entities.TransferOperation;
import fr.exalt.businessmicroserviceoperation.infrastructure.adapters.input.feignclient.models.BankAccountDto;
import fr.exalt.businessmicroserviceoperation.infrastructure.adapters.input.feignclient.models.BankAccountModel;
import fr.exalt.businessmicroserviceoperation.infrastructure.adapters.input.feignclient.models.CustomerModel;
import fr.exalt.businessmicroserviceoperation.infrastructure.adapters.output.models.dtos.OperationDto;
import fr.exalt.businessmicroserviceoperation.infrastructure.adapters.output.models.entities.OperationModel;
import fr.exalt.businessmicroserviceoperation.infrastructure.adapters.output.models.entities.TransferModel;
import org.springframework.beans.BeanUtils;

import java.time.Instant;
import java.util.UUID;

public class MapperService {
    private MapperService() {
    }

    public static Operation fromTo(OperationDto dto) {
        Operation operation = Operation.builder().build();
        BeanUtils.copyProperties(dto, operation);
        return operation;
    }

    public static Operation fromTo(OperationModel model) {
        Operation operation = Operation.builder().build();
        BeanUtils.copyProperties(model, operation);
        return operation;
    }

    public static BankAccount fromTo(BankAccountModel model){
        BankAccount bankAccount = BankAccount.builder().build();
        BeanUtils.copyProperties(model, bankAccount);
        return bankAccount;
    }
    public static BankAccountDto fromTo(BankAccount bankAccount){
        BankAccountDto bankAccountDto = BankAccountDto.builder().build();
        BeanUtils.copyProperties(bankAccount, bankAccountDto);
        return bankAccountDto;
    }
    public static Customer fromTo(CustomerModel model){
        Customer customer = Customer.builder().build();
        BeanUtils.copyProperties(model, customer);
        return customer;
    }

    public static TransferOperation fromTo(TransferModel model){
        TransferOperation operation = TransferOperation.builder().build();
        BeanUtils.copyProperties(model, operation);
        return operation;
    }

    public static OperationAvro from(Operation operation){
        CustomerAvro customerAvro = CustomerAvro.newBuilder()
                .setCustomerId(operation.getBankAccount().getCustomerId())
                .setFirstname(operation.getBankAccount().getCustomer().getFirstname())
                .setLastname(operation.getBankAccount().getCustomer().getLastname())
                .setEmail(operation.getBankAccount().getCustomer().getEmail())
                .setState(operation.getBankAccount().getCustomer().getState())
                .build();
        BankAccountAvro bankAccountAvro = BankAccountAvro.newBuilder()
                .setAccountId(operation.getAccountId())
                .setType(operation.getBankAccount().getType())
                .setState(operation.getBankAccount().getState())
                .setBalance(operation.getBankAccount().getBalance())
                .setOverdraft(operation.getBankAccount().getOverdraft())
                .setCustomerId(operation.getBankAccount().getCustomerId())
                .setCustomer(customerAvro)
                .build();
        return OperationAvro.newBuilder()
                .setOperationId(operation.getOperationId())
                .setType(operation.getType())
                .setMount(operation.getMount())
                .setCreatedAt(operation.getCreatedAt())
                .setAccountId(operation.getAccountId())
                .setBankAccount(bankAccountAvro)
                .build();
    }

    public static OperationModel from(OperationAvro avro){
        OperationModel model = OperationModel.builder().build();
        BeanUtils.copyProperties(avro,model);
        return model;
    }

    public static OriginAccountAvro fromOrigin(BankAccount bankAccount) {
        OriginCustomerAvro originCustomerAvro = OriginCustomerAvro.newBuilder()
                .setCustomerId(bankAccount.getCustomerId())
                .setFirstname(bankAccount.getCustomer().getFirstname())
                .setLastname(bankAccount.getCustomer().getLastname())
                .setEmail(bankAccount.getCustomer().getEmail())
                .setState(bankAccount.getCustomer().getState())
                .build();
        return OriginAccountAvro.newBuilder()
                .setAccountId(bankAccount.getAccountId())
                .setType(bankAccount.getType())
                .setState(bankAccount.getState())
                .setBalance(bankAccount.getBalance())
                .setCustomerId(bankAccount.getCustomerId())
                .setOriginCustomer(originCustomerAvro)
                .build();
    }

    public static DestinationAccountAvro fromDestination (BankAccount bankAccount){
        DestinationCustomerAvro destinationCustomerAvro= DestinationCustomerAvro.newBuilder()
                .setCustomerId(bankAccount.getCustomerId())
                .setFirstname(bankAccount.getCustomer().getFirstname())
                .setLastname(bankAccount.getCustomer().getLastname())
                .setEmail(bankAccount.getCustomer().getEmail())
                .setState(bankAccount.getCustomer().getState())
                .build();
        return DestinationAccountAvro.newBuilder()
                .setAccountId(bankAccount.getAccountId())
                .setType(bankAccount.getType())
                .setState(bankAccount.getState())
                .setBalance(bankAccount.getBalance())
                .setCustomerId(bankAccount.getCustomerId())
                .setDestinationCustomer(destinationCustomerAvro)
                .build();
    }

    public static BankAccountModel from(OriginAccountAvro avro){
        BankAccountModel model = BankAccountModel.builder().build();
        BeanUtils.copyProperties(avro, model);
        return model;
    }
    public static BankAccountModel from(DestinationAccountAvro avro){
        BankAccountModel model = BankAccountModel.builder().build();
        BeanUtils.copyProperties(avro, model);
        return model;
    }

    public static TransferModel from (TransferOperationAvro avro){
        OriginAccountAvro origin = avro.getOriginAccount();
        BankAccountModel originModel = MapperService.from(origin);
        DestinationAccountAvro destination = avro.getDestinationAccount();
        BankAccountModel destinationModel = MapperService.from(destination);
        return TransferModel.builder()
                .transferId(UUID.randomUUID().toString())
                .origin(origin.getAccountId())
                .accountOrigin(originModel)
                .destination(destination.getAccountId())
                .accountDestination(destinationModel)
                .mount(avro.getMount())
                .createdAt(Instant.now().toString())
                .build();
    }
}
