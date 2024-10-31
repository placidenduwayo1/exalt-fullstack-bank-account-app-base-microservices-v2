package fr.exalt.businessmicroservicespringsecurity.exceptions;

public class RoleNoAssignedTheUserException extends RuntimeException{
    public RoleNoAssignedTheUserException(String message) {
        super(message);
    }
}
