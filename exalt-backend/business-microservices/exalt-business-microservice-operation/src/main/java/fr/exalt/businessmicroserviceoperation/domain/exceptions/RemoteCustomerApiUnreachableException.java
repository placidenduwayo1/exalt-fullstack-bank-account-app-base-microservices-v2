package fr.exalt.businessmicroserviceoperation.domain.exceptions;

public class RemoteCustomerApiUnreachableException extends RuntimeException{
    public RemoteCustomerApiUnreachableException(String message) {
        super(message);
    }
}
