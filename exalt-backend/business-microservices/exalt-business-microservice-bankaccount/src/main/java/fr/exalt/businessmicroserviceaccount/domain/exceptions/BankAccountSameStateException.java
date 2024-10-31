package fr.exalt.businessmicroserviceaccount.domain.exceptions;

public class BankAccountSameStateException extends RuntimeException{
    public BankAccountSameStateException(String message) {
        super(message);
    }
}
