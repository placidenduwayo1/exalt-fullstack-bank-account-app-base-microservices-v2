package fr.exalt.businessmicroserviceoperation.domain.exceptions;

public class RemoteBankAccountApiUnreachableException extends RuntimeException{
    public RemoteBankAccountApiUnreachableException(String message) {
        super(message);
    }
}
