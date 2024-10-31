package fr.exalt.businessmicroserviceoperation.infrastructure.adapters.output.models.entities;

import fr.exalt.businessmicroserviceoperation.infrastructure.adapters.input.feignclient.models.BankAccountModel;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "operations")
public class OperationModel {
    @Id
    @UuidGenerator
    private String operationId;
    private String type;
    private double mount;
    private String createdAt;
    private String accountId;
    @Transient
    private BankAccountModel account;
}
