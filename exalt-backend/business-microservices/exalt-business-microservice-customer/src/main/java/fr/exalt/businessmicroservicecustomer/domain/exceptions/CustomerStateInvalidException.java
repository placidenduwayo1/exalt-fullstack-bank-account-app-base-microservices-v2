package fr.exalt.businessmicroservicecustomer.domain.exceptions;

public class CustomerStateInvalidException extends RuntimeException{
    public CustomerStateInvalidException(String message) {
        super(message);
    }
}
