package fr.exalt.businessmicroserviceoperation.infrastructure.config;

import fr.exalt.businessmicroserviceoperation.domain.ports.output.KafkaProducerService;
import fr.exalt.businessmicroserviceoperation.domain.ports.output.OutputOperationService;
import fr.exalt.businessmicroserviceoperation.domain.usecase.InputOperationServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class OperationConfig {
    @Bean
    public InputOperationServiceImpl config(OutputOperationService outputOperationService, KafkaProducerService kafkaProducer){
        return new InputOperationServiceImpl(outputOperationService, kafkaProducer);
    }
}
