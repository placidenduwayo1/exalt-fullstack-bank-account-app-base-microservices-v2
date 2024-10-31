package fr.exalt.businessmicroserviceoperation.domain.exceptions;

public class OperationRequestFieldsInvalidException extends RuntimeException{
    public OperationRequestFieldsInvalidException(String message) {
        super(message);
    }
}
