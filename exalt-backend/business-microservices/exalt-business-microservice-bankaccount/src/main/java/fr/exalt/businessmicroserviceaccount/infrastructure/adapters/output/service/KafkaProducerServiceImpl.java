package fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.service;

import fr.exalt.businessmicroserviceaccount.domain.avrobeans.CurrentBankAccountAvro;
import fr.exalt.businessmicroserviceaccount.domain.avrobeans.SavingBankAccountAvro;
import fr.exalt.businessmicroserviceaccount.domain.entities.BankAccount;
import fr.exalt.businessmicroserviceaccount.domain.entities.CurrentBankAccount;
import fr.exalt.businessmicroserviceaccount.domain.entities.SavingBankAccount;
import fr.exalt.businessmicroserviceaccount.domain.ports.output.KafkaProducerService;
import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.mapper.MapperService;
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
    private final KafkaTemplate<String, CurrentBankAccountAvro> currentAccountkafkaTemplate;
    private final KafkaTemplate<String, SavingBankAccountAvro> savingAccountkafkaTemplate;
    @Value("${kafka.topic.name}")
    private String[] topics;

    private final Logger logger = Logger.getLogger(this.getClass().getSimpleName());

    private Message<?> buildKafkaMessageCurrentAccount(CurrentBankAccountAvro avro, int partition) {
        return MessageBuilder
                .withPayload(avro)
                .setHeader(KafkaHeaders.TOPIC, topics[0])
                .setHeader(KafkaHeaders.PARTITION, partition)
                .build();
    }

    private Message<?> buildKafkaMessageSavingAccount(SavingBankAccountAvro avro, int partition) {
        return MessageBuilder
                .withPayload(avro)
                .setHeader(KafkaHeaders.TOPIC, topics[1])
                .setHeader(KafkaHeaders.PARTITION,
                        partition)
                .build();
    }


    @Override
    public void producerBankAccountModelCreateEvent(BankAccount bankAccount) {
        switch (bankAccount) {
            case CurrentBankAccount current -> {
                CurrentBankAccountAvro avro = MapperService.mapCurrentAccountToAvro(current);
                logger.log(Level.INFO,"send current bank account model {0} into topic",avro);
                currentAccountkafkaTemplate.send(buildKafkaMessageCurrentAccount(avro, 0));//partition 1 for current account to create
            }
            case SavingBankAccount saving -> {
                SavingBankAccountAvro avro = MapperService.mapSavingAccountToAvro(saving);
                logger.log(Level.INFO,"sending saving bank account model {0} into topic",avro);
                savingAccountkafkaTemplate.send(buildKafkaMessageSavingAccount(avro, 0));//partition 0 for saving account to create
            }
            default -> logger.log(Level.SEVERE,"{0}",bankAccount);
        }

    }


    @Override
    public void producerSavingAccountModelChangeInterestRate(SavingBankAccountAvro avro) {
        logger.log(Level.INFO,"sending saving bank account model {0} into topic",avro);
        savingAccountkafkaTemplate.send(buildKafkaMessageSavingAccount(avro, 1));//partition 1 for saving account to change interestRate
    }

    @Override
    public void producerCurrentAccountModelChangeOverdraft(CurrentBankAccountAvro avro) {
        logger.log(Level.INFO,"send current bank account model {0} into topic",avro);
        currentAccountkafkaTemplate.send(buildKafkaMessageCurrentAccount(avro, 1));//partition 1 for current account to change overdraft
    }

    @Override
    public void producerSavingAccountSwitchState(SavingBankAccountAvro avro) {
        logger.log(Level.INFO,"send saving bank account model {0} into topic",avro);
        savingAccountkafkaTemplate.send(buildKafkaMessageSavingAccount(avro, 1));
    }

    @Override
    public void producerCurrentAccountSwitchState(CurrentBankAccountAvro avro) {
        logger.log(Level.INFO,"sending current bank account model {0} into topic",avro);
        currentAccountkafkaTemplate.send(buildKafkaMessageCurrentAccount(avro, 1));
    }
}
