package fr.exalt.businessmicroservicecustomer.domain.exceptions;

public class EmailAlreadyTakenException extends RuntimeException{
    public EmailAlreadyTakenException(String message){
        super(message);
    }
}
