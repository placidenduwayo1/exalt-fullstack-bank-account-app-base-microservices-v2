package fr.exalt.businessmicroservicecustomer;

import fr.exalt.businessmicroservicecustomer.infrastructure.security.rsakey.RSAPublicKeyPicker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableConfigurationProperties(value = RSAPublicKeyPicker.class)
public class BusinessMicroserviceCustomerApplication {

	public static void main(String[] args) {
		new SpringApplication(BusinessMicroserviceCustomerApplication.class)
				.run(args);
	}

}
