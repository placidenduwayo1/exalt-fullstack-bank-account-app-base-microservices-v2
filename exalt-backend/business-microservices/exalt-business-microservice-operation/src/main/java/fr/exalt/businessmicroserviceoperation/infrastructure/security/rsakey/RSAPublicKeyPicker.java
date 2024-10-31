package fr.exalt.businessmicroserviceoperation.infrastructure.security.rsakey;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.security.interfaces.RSAPublicKey;

@ConfigurationProperties(prefix = "rsa")
public record RSAPublicKeyPicker(RSAPublicKey rsaPublicKey){
}
