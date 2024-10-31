package fr.exalt.businessmicroservicespringsecurity.exceptions;

public class RefreshTokenMissException extends RuntimeException {
    public RefreshTokenMissException(String refreshToken) {
        super(refreshToken);
    }
}
