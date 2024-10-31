package fr.exalt.businessmicroservicespringsecurity.entities.dtos;

import lombok.*;
@Setter
@Getter
public class UserDto {
    private String firstname;
    private String lastname;
    private String email;
    private String pwd;
    private String pwd1;
}
