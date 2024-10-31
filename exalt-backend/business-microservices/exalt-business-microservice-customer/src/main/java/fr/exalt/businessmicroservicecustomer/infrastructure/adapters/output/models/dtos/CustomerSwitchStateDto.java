package fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.models.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class CustomerSwitchStateDto {
    private String customerId;
    private String state;
}
