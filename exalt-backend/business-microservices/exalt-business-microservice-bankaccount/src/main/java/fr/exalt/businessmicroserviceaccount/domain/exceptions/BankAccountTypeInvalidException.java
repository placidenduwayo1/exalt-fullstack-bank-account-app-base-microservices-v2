package fr.exalt.businessmicroserviceaccount.domain.exceptions;

public class BankAccountTypeInvalidException extends RuntimeException{
    public BankAccountTypeInvalidException(String message) {
        super(message);
    }
}
