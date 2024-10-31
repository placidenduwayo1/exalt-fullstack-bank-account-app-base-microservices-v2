package fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.models.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "customers")
@Setter
@Getter
@ToString
public class CustomerModel {
    @Id
    @UuidGenerator
    private String customerId;
    private String firstname;
    private String lastname;
    private String state;
    private String email;
    private String createdAt;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "address_id" )
    private AddressModel address;
}
