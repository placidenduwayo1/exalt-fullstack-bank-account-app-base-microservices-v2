package fr.exalt.businessmicroservicespringsecurity.servicesecurity.securitywebtoken;

import fr.exalt.businessmicroservicespringsecurity.exceptions.RefreshTokenMissException;
import fr.exalt.businessmicroservicespringsecurity.exceptions.UserAuthenticationFailedException;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface JwtGeneratorService {
    ResponseEntity<Map<String, String>> generateJwt(JwtDto jwtDto);
}
