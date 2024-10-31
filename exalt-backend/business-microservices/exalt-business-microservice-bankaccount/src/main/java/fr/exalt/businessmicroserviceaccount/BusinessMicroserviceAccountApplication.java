package fr.exalt.businessmicroserviceaccount;

import fr.exalt.businessmicroserviceaccount.infrastructure.security.rsakey.RSAPublicKeyPicker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableConfigurationProperties(value = RSAPublicKeyPicker.class)
public class BusinessMicroserviceAccountApplication {
	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(BusinessMicroserviceAccountApplication.class);
		application.run(args);
	}
}
