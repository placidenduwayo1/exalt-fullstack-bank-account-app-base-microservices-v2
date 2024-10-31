package fr.exalt.businessmicroservicespringsecurity.servicesecurity.securitywebtoken;

import fr.exalt.businessmicroservicespringsecurity.exceptions.ExceptionE;
import fr.exalt.businessmicroservicespringsecurity.exceptions.RefreshTokenMissException;
import fr.exalt.businessmicroservicespringsecurity.exceptions.UserAuthenticationFailedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtGeneratorServiceImpl implements JwtGeneratorService {
    private final AuthenticationManager authenticationManager;
    private final JwtDecoder jwtDecoder;
    private final JwtEncoder jwtEncoder;
    private final UserDetailsService userDetailsService;
    private static final short ACCESS_TOKEN = 2;
    private static final short REFRESH_TOKEN = 48;
    private static final String ISSUER = "business ms spring security aouth2 resource server";
    private static final String GRANT_TYPE1 = "username&pwd";
    private static final String GRANT_TYPE2 = "refresh-token";

    @Override
    public ResponseEntity<Map<String, String>> generateJwt(JwtDto jwtDto) {
        String username=null;
        Collection<String> roles = new ArrayList<>();
        Map<String, String> tokens = new HashMap<>();
        if (jwtDto.getGrantType().equals(GRANT_TYPE1)) {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(
                            jwtDto.getUsername(), jwtDto.getPwd()));
            if (authentication.isAuthenticated()) {
                username = authentication.getName();
                roles = authentication.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList();
            } else {
                throw new UserAuthenticationFailedException(ExceptionE.USER_AUTH_FAIL);
            }
        } else if (jwtDto.getGrantType().equals(GRANT_TYPE2)) {
            if (jwtDto.getRefreshToken() == null) {
                throw new RefreshTokenMissException(ExceptionE.REFRESH_TOKEN);
            }
            Jwt jwt;
            try {
              jwt  = jwtDecoder.decode(jwtDto.getRefreshToken());
                username = jwt.getSubject();
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
            } catch (Exception exception) {
                return new ResponseEntity<>(Map.of("refresh-token-error", exception.getMessage()), HttpStatus.UNAUTHORIZED);
            }
        }

        Instant instant = Instant.now();
        assert username != null;
        JwtClaimsSet jwtClaimsSet1 = JwtClaimsSet.builder()
                .subject(username)
                .issuedAt(instant)
                .expiresAt(instant.plus(ACCESS_TOKEN, ChronoUnit.HOURS))
                .issuer(ISSUER)
                .claim("scope", roles)
                .build();
        String jwtAccessToken = jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet1)).getTokenValue();
        tokens.put("access-token-key", jwtAccessToken);

        if (jwtDto.isWithRefreshToken()) {
            JwtClaimsSet jwtClaimsSet2 = JwtClaimsSet.builder()
                    .subject(username)
                    .issuer(ISSUER)
                    .issuedAt(instant)
                    .expiresAt(instant.plus(REFRESH_TOKEN, ChronoUnit.HOURS))
                    .build();
            String jwtRefreshToken = jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet2)).getTokenValue();
            tokens.put("refresh-token-key", jwtRefreshToken);
        }

        return new ResponseEntity<>(tokens, HttpStatus.OK);
    }
}
