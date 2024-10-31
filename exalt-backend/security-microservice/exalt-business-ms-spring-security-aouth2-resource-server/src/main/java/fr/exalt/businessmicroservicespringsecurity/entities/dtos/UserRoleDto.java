package fr.exalt.businessmicroservicespringsecurity.entities.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UserRoleDto {
    private Long userId;
    private String roleName;
}
