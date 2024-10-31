package fr.exalt.businessmicroserviceoperation.infrastructure.adapters.input.feignclient.proxies;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/*** this class is an implementation of request interceptor between microservices
 *  that communicate between each other*/
@Slf4j
public class RemoteAccountCustomerRequestInterceptor implements RequestInterceptor {
    private static final String REGEX = "^Bearer ([a-zA-Z0-9-._~+/]+=*)$";
    private static final Pattern BEARER_TOKEN_HEADER_PATTERN =
            Pattern.compile(REGEX, Pattern.CASE_INSENSITIVE);
    private static final String AUTHORIZATION = HttpHeaders.AUTHORIZATION;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (Objects.nonNull(requestAttributes)) {
            String authorizationHeader = requestAttributes.getRequest().getHeader(AUTHORIZATION);
            Matcher matcher = BEARER_TOKEN_HEADER_PATTERN.matcher(authorizationHeader);
            if (matcher.matches()) {
                requestTemplate.header(AUTHORIZATION, authorizationHeader);
            } else {
                log.error("[OPERATION-BS-MICROSERVICE] authorization not match");
            }
        } else {
            log.error("[OPERATION-BS-MICROSERVICE] Request Attributes null");
        }

    }
}
