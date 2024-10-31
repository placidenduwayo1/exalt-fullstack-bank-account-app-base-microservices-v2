package fr.exalt.businessmicroserviceoperation.domain.exceptions;

public class RemoteAccountSuspendedException extends RuntimeException{
    public RemoteAccountSuspendedException(String message) {
        super(message);
    }
}
