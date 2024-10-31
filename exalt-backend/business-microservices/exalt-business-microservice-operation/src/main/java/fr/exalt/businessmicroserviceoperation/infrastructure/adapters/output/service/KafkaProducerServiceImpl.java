package fr.exalt.businessmicroserviceoperation.infrastructure.adapters.output.service;

import fr.exalt.businessmicroserviceoperation.domain.avromodels.operation.OperationAvro;
import fr.exalt.businessmicroserviceoperation.domain.avromodels.transfer.TransferOperationAvro;
import fr.exalt.businessmicroserviceoperation.domain.ports.output.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.logging.Level;
import java.util.logging.Logger;

@Component
@RequiredArgsConstructor
public class KafkaProducerServiceImpl implements KafkaProducerService {
    private final Logger logger = Logger.getLogger(this.getClass().getSimpleName());
    private final KafkaTemplate<String, OperationAvro> kafkaTemplate1;
    private final KafkaTemplate<String, TransferOperationAvro> kafkaTemplate2;
    @Value("${kafka.topic.name}")
    private String [] topics;

    private Message<?> buildOperationKafkaMessage(OperationAvro avro){
        return MessageBuilder.withPayload(avro)
                .setHeader(KafkaHeaders.TOPIC,topics[0])
                .setHeader(KafkaHeaders.PARTITION, 0)
                .build();
    }
    private Message<?> buildTransferOperationKafkaMessage(TransferOperationAvro avro){
        return MessageBuilder.withPayload(avro)
                .setHeader(KafkaHeaders.TOPIC,topics[1])
                .setHeader(KafkaHeaders.PARTITION,0)
                .build();
    }
    @Override
    public void producerOperationAvroModelCreateEvent(OperationAvro avro) {
        logger.log(Level.INFO,"sending operation payload {0} into topic",avro);
        kafkaTemplate1.send(buildOperationKafkaMessage(avro));
    }

    @Override
    public void producerTransferOperation(TransferOperationAvro avro) {
        logger.log(Level.INFO,"sending transfer operation payload {0} into topic",avro);
        kafkaTemplate2.send(buildTransferOperationKafkaMessage(avro));
    }
}
