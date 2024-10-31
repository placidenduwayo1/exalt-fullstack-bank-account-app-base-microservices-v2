package fr.exalt.businessmicroserviceaccount.domain.exceptions;

public class BankAccountStateInvalidException extends RuntimeException {
    public BankAccountStateInvalidException(String message) {
        super(message);
    }
}
