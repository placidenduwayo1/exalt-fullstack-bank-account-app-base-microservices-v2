package fr.exalt.businessmicroservicespringsecurity.entities.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UserUpdateDto {
    private String firstname;
    private String lastname;
    private String email;
}
