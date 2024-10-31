package fr.exalt.businessmicroservicecustomer.domain.ports.output;

import fr.exalt.businessmicroservicecustomer.domain.avrobeans.CustomerAvro;

public interface KafkaProducerService {
    void producerCustomerModelCreateEvent(CustomerAvro avro);
    void producerCustomerModelUpdateEvent(CustomerAvro avro);
    void producerCustomerModelSwitchState(CustomerAvro avro);
}
