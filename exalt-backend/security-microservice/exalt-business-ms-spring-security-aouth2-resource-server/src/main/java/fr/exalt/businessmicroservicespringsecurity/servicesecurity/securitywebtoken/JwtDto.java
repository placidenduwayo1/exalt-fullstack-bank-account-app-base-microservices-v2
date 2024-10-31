package fr.exalt.businessmicroservicespringsecurity.servicesecurity.securitywebtoken;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtDto {
    private String username;
    private String pwd;
    private String grantType;
    private boolean withRefreshToken;
    private String refreshToken;
}
