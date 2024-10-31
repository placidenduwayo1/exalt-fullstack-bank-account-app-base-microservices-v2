package fr.exalt.businessmicroserviceaccount.domain.ports.output;

import fr.exalt.businessmicroserviceaccount.domain.avrobeans.CurrentBankAccountAvro;
import fr.exalt.businessmicroserviceaccount.domain.avrobeans.SavingBankAccountAvro;
import fr.exalt.businessmicroserviceaccount.domain.entities.BankAccount;
import fr.exalt.businessmicroserviceaccount.domain.entities.CurrentBankAccount;
import fr.exalt.businessmicroserviceaccount.domain.entities.Customer;
import fr.exalt.businessmicroserviceaccount.domain.entities.SavingBankAccount;

import java.util.Collection;

public interface OutputAccountService {
    BankAccount createCurrentAccountKafkaConsumer(CurrentBankAccountAvro avro);
    BankAccount createSavingAccountKafkaConsumer(SavingBankAccountAvro avro);
    Collection<BankAccount> getAllAccounts();

    BankAccount getAccount(String accountId);

    Collection<BankAccount> getAccountOfGivenCustomer(String customerId);

    Customer loadRemoteCustomer(String customerId);

    CurrentBankAccount updateCurrentAccount(CurrentBankAccount currentAccount);
    SavingBankAccount updateSavingAccount(SavingBankAccount savingBankAccount);
    BankAccount switchAccountStateKafkaConsumer(BankAccount bankAccount);

    BankAccount changeOverdraftKafkaConsumer(CurrentBankAccountAvro avro);

    BankAccount changeInterestRateKafkaConsumer(SavingBankAccountAvro avro);

    Collection<BankAccount> getAllSuspendedAccounts();

    Collection<BankAccount> getAllRiskAccounts();
}
