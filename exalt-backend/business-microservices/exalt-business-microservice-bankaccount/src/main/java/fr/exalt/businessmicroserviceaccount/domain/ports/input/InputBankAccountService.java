package fr.exalt.businessmicroserviceaccount.domain.ports.input;

import fr.exalt.businessmicroserviceaccount.domain.entities.BankAccount;
import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.models.dtos.BankAccountDto;
import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.models.dtos.BankAccountInterestRateDto;
import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.models.dtos.BankAccountOverdraftDto;
import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.models.dtos.BankAccountSwitchStatedDto;

import java.util.Collection;

public interface InputBankAccountService {
    BankAccount createAccountKafkaEvent(BankAccountDto bankAccountDto);

    Collection<BankAccount> getAllAccounts();

    BankAccount getAccount(String accountId);

    Collection<BankAccount> getAccountOfGivenCustomer(String customerId);

    BankAccount updateAccount(String accountId, BankAccountDto bankAccountDto) ;
    BankAccount switchAccountState(BankAccountSwitchStatedDto dto);

    BankAccount changeOverdraft(BankAccountOverdraftDto dto);

    BankAccount changeInterestRate(BankAccountInterestRateDto dto);
    Collection<BankAccount> getAllSuspendedAccounts();
    Collection<BankAccount> getAllRiskAccounts();
}

