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
@ToString
@Entity
@Table(name = "transfers")
public class TransferModel {
    @Id
    @UuidGenerator
    private String transferId;
    private String origin;
    @Transient
    private BankAccountModel accountOrigin;
    private String destination;
    @Transient
    private BankAccountModel accountDestination;
    private double mount;
    private String createdAt;
}
