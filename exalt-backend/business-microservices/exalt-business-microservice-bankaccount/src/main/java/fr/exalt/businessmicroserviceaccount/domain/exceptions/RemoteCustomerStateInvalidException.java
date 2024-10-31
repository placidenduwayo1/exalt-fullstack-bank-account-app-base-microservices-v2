package fr.exalt.businessmicroserviceaccount.domain.exceptions;

public class RemoteCustomerStateInvalidException extends RuntimeException{
    public RemoteCustomerStateInvalidException(String message) {
        super(message);
    }
}
