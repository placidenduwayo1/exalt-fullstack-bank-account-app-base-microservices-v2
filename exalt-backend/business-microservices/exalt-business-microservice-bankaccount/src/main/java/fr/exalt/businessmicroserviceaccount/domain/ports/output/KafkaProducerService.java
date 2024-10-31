package fr.exalt.businessmicroserviceaccount.domain.ports.output;

import fr.exalt.businessmicroserviceaccount.domain.avrobeans.CurrentBankAccountAvro;
import fr.exalt.businessmicroserviceaccount.domain.avrobeans.SavingBankAccountAvro;
import fr.exalt.businessmicroserviceaccount.domain.entities.BankAccount;

public interface KafkaProducerService {
    void producerBankAccountModelCreateEvent(BankAccount bankAccount);
    void producerSavingAccountModelChangeInterestRate(SavingBankAccountAvro avro);
    void producerCurrentAccountModelChangeOverdraft(CurrentBankAccountAvro avro);

    void producerSavingAccountSwitchState(SavingBankAccountAvro avro);

    void producerCurrentAccountSwitchState(CurrentBankAccountAvro currentBankAccountAvro);
}
