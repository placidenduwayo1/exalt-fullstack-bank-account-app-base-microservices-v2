package fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.models.dtos;

import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AddressDto {
    private int streetNum;
    private String streetName;
    private int poBox;
    private String city;
    private String country;
}
