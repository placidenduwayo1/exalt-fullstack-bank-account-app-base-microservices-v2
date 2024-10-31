package fr.exalt.businessmicroserviceaccount.infrastructure.config;

import fr.exalt.businessmicroserviceaccount.domain.ports.output.KafkaProducerService;
import fr.exalt.businessmicroserviceaccount.domain.ports.output.OutputAccountService;
import fr.exalt.businessmicroserviceaccount.domain.usecase.InputBankAccountServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class AccountConfig {
    @Bean
    public InputBankAccountServiceImpl config(OutputAccountService outputAccountService,
                                              KafkaProducerService kafkaProducer){
        return new InputBankAccountServiceImpl(outputAccountService, kafkaProducer);
    }
}
