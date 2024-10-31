package fr.exalt.businessmicroserviceoperation;

import fr.exalt.businessmicroserviceoperation.infrastructure.security.rsakey.RSAPublicKeyPicker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableConfigurationProperties(value = RSAPublicKeyPicker.class)
public class BusinessMicroserviceOperationApplication {
	public static void main(String[] args) {
		new SpringApplication(BusinessMicroserviceOperationApplication.class)
				.run(args);
	}
}
