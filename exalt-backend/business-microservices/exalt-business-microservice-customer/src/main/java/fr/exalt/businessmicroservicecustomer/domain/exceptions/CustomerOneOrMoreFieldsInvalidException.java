package fr.exalt.businessmicroservicecustomer.domain.exceptions;

public class CustomerOneOrMoreFieldsInvalidException extends RuntimeException{
    public CustomerOneOrMoreFieldsInvalidException(String message) {
        super(message);
    }
}
