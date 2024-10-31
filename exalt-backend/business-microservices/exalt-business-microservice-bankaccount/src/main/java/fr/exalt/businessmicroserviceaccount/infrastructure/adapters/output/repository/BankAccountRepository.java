package fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.repository;

import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.models.entities.BankAccountModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface BankAccountRepository extends JpaRepository<BankAccountModel, String> {
    Collection<BankAccountModel> findByCustomerId(String customerId);
    @Query(value = "SELECT * FROM bank_accounts  where bank_accounts.state='suspended'", nativeQuery = true)
    Collection<BankAccountModel> findAllDeactivate();
    @Query(value = "select * from bank_accounts where bank_accounts.balance <=100", nativeQuery = true)
    Collection<BankAccountModel> findAllRiskAccounts();
    @Query(value = "select * from bank_accounts", nativeQuery = true)
    Collection<BankAccountModel> findAllAccounts();
}
