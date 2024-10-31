package fr.exalt.businessmicroservicespringsecurity.servicesecurity.rsakeyspicker;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
@ConfigurationProperties(prefix = "rsa")
public record RSAKeysPairPicker(RSAPublicKey publicKey, RSAPrivateKey privateKey) {
}
