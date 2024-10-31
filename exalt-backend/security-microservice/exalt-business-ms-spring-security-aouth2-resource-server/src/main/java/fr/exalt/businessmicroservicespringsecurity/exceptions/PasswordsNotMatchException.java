package fr.exalt.businessmicroservicespringsecurity.exceptions;

public class PasswordsNotMatchException extends RuntimeException {
    public PasswordsNotMatchException(String passwordMatch) {
        super(passwordMatch);
    }
}
