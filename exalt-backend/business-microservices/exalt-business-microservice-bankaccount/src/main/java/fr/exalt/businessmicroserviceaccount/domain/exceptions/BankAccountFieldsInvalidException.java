package fr.exalt.businessmicroserviceaccount.domain.exceptions;

public class BankAccountFieldsInvalidException extends RuntimeException{
    public BankAccountFieldsInvalidException(String message) {
        super(message);
    }
}
