package fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.models.dtos;

import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RequestDto {
    private CustomerDto customerDto;
    private AddressDto addressDto;
}
