package fr.exalt.businessmicroserviceoperation.infrastructure.adapters.output.repositories;

import fr.exalt.businessmicroserviceoperation.infrastructure.adapters.output.models.entities.OperationModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface OperationRepository extends JpaRepository<OperationModel, String> {
    Collection<OperationModel> findByAccountId(String accountId);
    @Query(value = "select * from operations", nativeQuery = true)
    Collection<OperationModel> findAllOperations();
    @Query(value = "select * from operations where operations.type='depot'", nativeQuery = true)
    Collection<OperationModel> getAllDepositOperation();
    @Query(value = "select * from operations where operations.type='retrait'",nativeQuery = true)
    Collection<OperationModel> getAllWithdrawalsOperation();
}
