package fr.exalt.businessmicroserviceoperation.domain.ports.output;

import fr.exalt.businessmicroserviceoperation.domain.avromodels.operation.OperationAvro;
import fr.exalt.businessmicroserviceoperation.domain.avromodels.transfer.TransferOperationAvro;

public interface KafkaProducerService {
    void producerOperationAvroModelCreateEvent(OperationAvro avro);

    void producerTransferOperation(TransferOperationAvro transferOperationAvro);
}
