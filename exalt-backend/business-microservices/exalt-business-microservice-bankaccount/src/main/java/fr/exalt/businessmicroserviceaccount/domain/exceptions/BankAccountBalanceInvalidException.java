package fr.exalt.businessmicroserviceaccount.domain.exceptions;

public class BankAccountBalanceInvalidException extends RuntimeException{
    public BankAccountBalanceInvalidException(String message) {
        super(message);
    }
}
