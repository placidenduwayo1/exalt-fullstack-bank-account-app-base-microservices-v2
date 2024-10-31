package fr.exalt.businessmicroserviceaccount.domain.exceptions;

public class BankAccountSuspendException extends RuntimeException{
    public BankAccountSuspendException(String message) {
        super(message);
    }
}
