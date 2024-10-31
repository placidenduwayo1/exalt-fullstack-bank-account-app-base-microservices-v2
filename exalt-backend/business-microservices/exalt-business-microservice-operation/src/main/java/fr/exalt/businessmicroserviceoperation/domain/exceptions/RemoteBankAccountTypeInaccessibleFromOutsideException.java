package fr.exalt.businessmicroserviceoperation.domain.exceptions;

public class RemoteBankAccountTypeInaccessibleFromOutsideException extends RuntimeException{
    public RemoteBankAccountTypeInaccessibleFromOutsideException(String message) {
        super(message);
    }
}
