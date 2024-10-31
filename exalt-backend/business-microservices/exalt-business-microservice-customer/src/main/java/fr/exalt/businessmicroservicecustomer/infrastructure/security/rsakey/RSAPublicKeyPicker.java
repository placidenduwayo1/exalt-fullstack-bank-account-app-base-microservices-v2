package fr.exalt.businessmicroservicecustomer.infrastructure.security.rsakey;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.security.interfaces.RSAPublicKey;

@ConfigurationProperties(prefix = "rsa")
public record RSAPublicKeyPicker(RSAPublicKey rsaPublicKey){
}
