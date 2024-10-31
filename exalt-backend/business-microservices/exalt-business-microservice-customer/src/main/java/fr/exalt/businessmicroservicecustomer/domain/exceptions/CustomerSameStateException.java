package fr.exalt.businessmicroservicecustomer.domain.exceptions;

public class CustomerSameStateException extends RuntimeException {
    public CustomerSameStateException(String message) {
        super(message);
    }
}
