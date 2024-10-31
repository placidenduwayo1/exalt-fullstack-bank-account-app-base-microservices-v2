package fr.exalt.businessmicroserviceaccount.domain.exceptions;

public class BankAccountOverdraftInvalidException extends RuntimeException{
    public BankAccountOverdraftInvalidException(String message) {
        super(message);
    }
}
