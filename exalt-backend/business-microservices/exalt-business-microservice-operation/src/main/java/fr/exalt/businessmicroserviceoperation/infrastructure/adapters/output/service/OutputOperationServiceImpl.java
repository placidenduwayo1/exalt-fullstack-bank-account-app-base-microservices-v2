package fr.exalt.businessmicroserviceoperation.infrastructure.adapters.output.service;

import fr.exalt.businessmicroserviceoperation.domain.avromodels.operation.OperationAvro;
import fr.exalt.businessmicroserviceoperation.domain.avromodels.transfer.TransferOperationAvro;
import fr.exalt.businessmicroserviceoperation.domain.entities.BankAccount;
import fr.exalt.businessmicroserviceoperation.domain.entities.Customer;
import fr.exalt.businessmicroserviceoperation.domain.entities.Operation;
import fr.exalt.businessmicroserviceoperation.domain.entities.TransferOperation;
import fr.exalt.businessmicroserviceoperation.domain.ports.output.OutputOperationService;
import fr.exalt.businessmicroserviceoperation.infrastructure.adapters.input.feignclient.models.BankAccountDto;
import fr.exalt.businessmicroserviceoperation.infrastructure.adapters.input.feignclient.models.BankAccountModel;
import fr.exalt.businessmicroserviceoperation.infrastructure.adapters.input.feignclient.models.CustomerModel;
import fr.exalt.businessmicroserviceoperation.infrastructure.adapters.input.feignclient.proxies.RemoteAccountServiceProxy;
import fr.exalt.businessmicroserviceoperation.infrastructure.adapters.input.feignclient.proxies.RemoteCustomerServiceProxy;
import fr.exalt.businessmicroserviceoperation.infrastructure.adapters.output.mapper.MapperService;
import fr.exalt.businessmicroserviceoperation.infrastructure.adapters.output.models.entities.OperationModel;
import fr.exalt.businessmicroserviceoperation.infrastructure.adapters.output.models.entities.TransferModel;
import fr.exalt.businessmicroserviceoperation.infrastructure.adapters.output.repositories.OperationRepository;
import fr.exalt.businessmicroserviceoperation.infrastructure.adapters.output.repositories.TransferRepository;
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
public class OutputOperationServiceImpl implements OutputOperationService {
    private final RemoteAccountServiceProxy remoteAccountServiceProxy;
    private final RemoteCustomerServiceProxy remoteCustomerServiceProxy;
    private final OperationRepository operationRepository;
    private final TransferRepository transferRepository;
    private final Logger logger = Logger.getLogger(this.getClass().getSimpleName());

    public OutputOperationServiceImpl(@Qualifier(value = "accountserviceproxy") RemoteAccountServiceProxy remoteAccountServiceProxy,
                                      @Qualifier(value = "customerserviceproxy") RemoteCustomerServiceProxy remoteCustomerServiceProxy,
                                      OperationRepository operationRepository, TransferRepository transferRepository) {
        this.remoteAccountServiceProxy = remoteAccountServiceProxy;
        this.remoteCustomerServiceProxy = remoteCustomerServiceProxy;
        this.operationRepository = operationRepository;
        this.transferRepository = transferRepository;
    }

    @Override
    //define Kafka consumer
    @KafkaListener(topicPartitions = @TopicPartition(topic = "depot-retrait-operation",
            partitionOffsets = @PartitionOffset(partition = "0", initialOffset = "0"))
    )
    public Operation createOperationKafkaConsumer(@Payload OperationAvro avroModel) {
        logger.log(Level.INFO,"operation payload consumed {0}",avroModel);
        OperationModel model = MapperService.from(avroModel);
        OperationModel savedModel = operationRepository.save(model);
        return MapperService.fromTo(savedModel);
    }

    @Override
    public Collection<Operation> getAllOperations() {
        return mapToOperation(operationRepository.findAllOperations());
    }

    @Override
    public BankAccount loadRemoteAccount(String accountId) {
        BankAccountModel model = remoteAccountServiceProxy.loadRemoteAccount(accountId);
        return MapperService.fromTo(model);
    }

    @Override
    public Customer loadRemoteCustomer(String customerId) {
        CustomerModel model = remoteCustomerServiceProxy.loadRemoteCustomer(customerId);
        return MapperService.fromTo(model);
    }

    @Override
    public BankAccount updateRemoteAccount(String accountId, BankAccountDto bankAccountDto) {
        BankAccountModel model = remoteAccountServiceProxy.updateRemoteAccount(accountId, bankAccountDto);
        return MapperService.fromTo(model);
    }

    @Override
    public Collection<Operation> getAccountOperations(String accountId) {
        return mapToOperation(operationRepository.findByAccountId(accountId));
    }

    @Override
    //kafka consumer
    @KafkaListener(groupId = "group1", autoStartup = "true",
    topicPartitions = @TopicPartition(topic = "transfer-operation",partitions = {"0"}))
    public void transfer(@Payload TransferOperationAvro avro) {
        logger.log(Level.INFO,"consumed  transfer operation avro {0}", avro);
        TransferModel model = MapperService.from(avro);
        logger.log(Level.INFO,"transfer operation model to save {0}", model);
        transferRepository.save(model);
    }

    @Override
    public Collection<TransferOperation> getAllTransfer() {
        Collection<TransferModel> models = transferRepository.findAll();
        return models.stream().map(MapperService::fromTo).toList();
    }

    @Override
    public Collection<Operation> getAllDepositOperation() {
        return mapToOperation(operationRepository.getAllDepositOperation());
    }

    @Override
    public Collection<Operation> getAllWithdrawalsOperation() {
        return mapToOperation(operationRepository.getAllWithdrawalsOperation());
    }

    private Collection<Operation> mapToOperation(Collection<OperationModel> models) {
        return models.stream()
                .map(MapperService::fromTo)
                .toList();
    }
}
