package fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.models.dtos;

import lombok.*;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {
    private String firstname;
    private String lastname;
    private String email;
}
