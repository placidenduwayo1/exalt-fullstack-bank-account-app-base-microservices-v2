package fr.exalt.businessmicroservicespringsecurity.exceptions;

public class RoleInformationInvalidException extends RuntimeException {
    public RoleInformationInvalidException(String roleInfo) {
        super(roleInfo);
    }
}
