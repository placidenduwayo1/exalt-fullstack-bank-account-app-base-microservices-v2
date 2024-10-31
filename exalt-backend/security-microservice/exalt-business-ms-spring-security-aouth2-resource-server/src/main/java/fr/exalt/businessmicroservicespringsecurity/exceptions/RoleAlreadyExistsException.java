package fr.exalt.businessmicroservicespringsecurity.exceptions;

public class RoleAlreadyExistsException extends RuntimeException {
    public RoleAlreadyExistsException(String roleExists) {
        super(roleExists);
    }
}
