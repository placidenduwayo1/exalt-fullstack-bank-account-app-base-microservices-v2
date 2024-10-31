package fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.models.entities;

import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.input.feignclient.models.CustomerModel;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "bank_accounts")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", length = 7)
public abstract class BankAccountModel {
    @Id
    @UuidGenerator
    @Column(name = "account_id")
    private String accountId;
    private String state;
    private double balance;
    @Column(name = "created_date")
    private String createdAt;
    @Column(name = "customer_id")
    private String customerId;
    @Transient
    private CustomerModel customerModel;
}
