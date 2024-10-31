package fr.exalt.businessmicroservicespringsecurity.exceptions;

public class UserPossessThisRoleException extends RuntimeException {
    public UserPossessThisRoleException(String message) {
        super(message);
    }
}
