package fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.models.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "addresses")
@Setter
@Getter
public class AddressModel {
    @Id
    @UuidGenerator
    private String addressId;
    private int streetNum;
    private String streetName;
    private int poBox;
    private String city;
    private String country;
}
