package fr.exalt.businessmicroservicespringsecurity;

import fr.exalt.businessmicroservicespringsecurity.servicesecurity.rsakeyspicker.RSAKeysPairPicker;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(RSAKeysPairPicker.class)
public class BusinessMicroserviceSpringSecurityApplication {

    public static void main(String[] args) {

        new SpringApplicationBuilder(BusinessMicroserviceSpringSecurityApplication.class)
                .run(args);
    }
}
