package fr.exalt.businessmicroserviceoperation.domain.exceptions;

public class RemoteCustomerStateInvalidException extends RuntimeException{
    public RemoteCustomerStateInvalidException(String message) {
        super(message);
    }
}
