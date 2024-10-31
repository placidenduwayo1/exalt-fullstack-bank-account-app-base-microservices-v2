package fr.exalt.businessmicroservicespringsecurity.exceptions;

public class UserInformationInvalidException extends RuntimeException{
    public UserInformationInvalidException(String message) {
        super(message);
    }
}
