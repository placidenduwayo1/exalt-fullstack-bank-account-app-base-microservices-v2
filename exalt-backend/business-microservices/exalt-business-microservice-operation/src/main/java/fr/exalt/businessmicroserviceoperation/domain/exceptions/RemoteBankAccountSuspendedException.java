package fr.exalt.businessmicroserviceoperation.domain.exceptions;

public class RemoteBankAccountSuspendedException extends RuntimeException{
    public RemoteBankAccountSuspendedException(String message) {
        super(message);
    }
}
