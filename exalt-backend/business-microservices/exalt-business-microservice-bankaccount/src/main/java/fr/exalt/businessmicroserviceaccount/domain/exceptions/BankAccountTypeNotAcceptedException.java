package fr.exalt.businessmicroserviceaccount.domain.exceptions;

public class BankAccountTypeNotAcceptedException extends RuntimeException{
    public BankAccountTypeNotAcceptedException(String message) {
        super(message);
    }
}
