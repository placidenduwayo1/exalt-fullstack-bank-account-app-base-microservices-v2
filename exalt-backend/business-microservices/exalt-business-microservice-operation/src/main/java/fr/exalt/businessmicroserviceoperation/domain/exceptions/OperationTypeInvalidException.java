package fr.exalt.businessmicroserviceoperation.domain.exceptions;

public class OperationTypeInvalidException extends RuntimeException{
    public OperationTypeInvalidException(String message) {
        super(message);
    }
}
