package fr.exalt.businessmicroservicespringsecurity.exceptions;

public class UserAuthenticationFailedException extends RuntimeException{
    public UserAuthenticationFailedException(String message) {
        super(message);
    }
}
