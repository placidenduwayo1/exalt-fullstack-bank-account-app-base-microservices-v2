package fr.exalt.businessmicroserviceaccount.infrastructure.security;

import fr.exalt.businessmicroserviceaccount.infrastructure.security.rsakey.RSAPublicKeyPicker;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class BankAccountSecurityConfig {
    private final RSAPublicKeyPicker publicKeyPicker;

    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(publicKeyPicker.rsaPublicKey())
                .build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(httpRequest -> httpRequest
                        .requestMatchers(HttpMethod.POST,
                                "/api-bank-account/**").hasAnyAuthority("SCOPE_Admin", "SCOPE_CTO", "SCOPE_HR"))
                .authorizeHttpRequests(httpRequest -> httpRequest.requestMatchers(
                        HttpMethod.PUT,
                        "/api-bank-account/**").hasAnyAuthority("SCOPE_Admin", "SCOPE_CTO", "SCOPE_HR"))
                .authorizeHttpRequests(httpRequest -> httpRequest.anyRequest().authenticated())
                .oauth2ResourceServer(aouth2 -> aouth2.jwt(Customizer.withDefaults()));

        return httpSecurity.build();
    }
}
