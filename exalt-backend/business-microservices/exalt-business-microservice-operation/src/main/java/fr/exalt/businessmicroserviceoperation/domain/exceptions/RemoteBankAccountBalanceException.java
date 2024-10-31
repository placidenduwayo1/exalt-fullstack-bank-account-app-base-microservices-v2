package fr.exalt.businessmicroserviceoperation.domain.exceptions;

public class RemoteBankAccountBalanceException extends RuntimeException{
    public RemoteBankAccountBalanceException(String message) {
        super(message);
    }
}
