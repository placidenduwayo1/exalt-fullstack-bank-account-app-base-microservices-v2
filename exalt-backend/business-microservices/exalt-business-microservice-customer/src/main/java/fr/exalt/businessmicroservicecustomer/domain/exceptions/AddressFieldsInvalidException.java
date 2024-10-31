package fr.exalt.businessmicroservicecustomer.domain.exceptions;

public class AddressFieldsInvalidException extends RuntimeException {
    public AddressFieldsInvalidException(String message) {
        super(message);
    }
}
