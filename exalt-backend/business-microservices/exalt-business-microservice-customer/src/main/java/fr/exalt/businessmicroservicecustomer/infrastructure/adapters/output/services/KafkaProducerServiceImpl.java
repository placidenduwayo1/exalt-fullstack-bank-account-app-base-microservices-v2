package fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.services;

import fr.exalt.businessmicroservicecustomer.domain.avrobeans.CustomerAvro;
import fr.exalt.businessmicroservicecustomer.domain.ports.output.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class KafkaProducerServiceImpl implements KafkaProducerService {
    private final KafkaTemplate<String, CustomerAvro> customerAvroKafkaTemplate;
    @Value("${kafka.topic.name}")
    private String topic;
    private final Logger logger = Logger.getLogger(this.getClass().getSimpleName());

    @Override
    public void producerCustomerModelCreateEvent(CustomerAvro avro) {
        logger.log(Level.INFO,"sending customer payload {0} into topic partition 0",avro);
        customerAvroKafkaTemplate.send(buildCustomerMessage(avro,0));
    }

    @Override
    public void producerCustomerModelUpdateEvent(CustomerAvro avro) {
        logger.log(Level.INFO,"sending customer payload {0} into topic",avro);
        customerAvroKafkaTemplate.send(buildCustomerMessage(avro,1));
    }

    @Override
    public void producerCustomerModelSwitchState(CustomerAvro avro) {
        logger.log(Level.INFO,"sending customer payload {0} into topic partition 1",avro);
        customerAvroKafkaTemplate.send(buildCustomerMessage(avro,1));
    }

    private Message<?> buildCustomerMessage(CustomerAvro avro, int partition) {
        return MessageBuilder.withPayload(avro)
                .setHeader(KafkaHeaders.TOPIC, topic)
                .setHeader(KafkaHeaders.PARTITION, partition)
                .build();
    }
}
