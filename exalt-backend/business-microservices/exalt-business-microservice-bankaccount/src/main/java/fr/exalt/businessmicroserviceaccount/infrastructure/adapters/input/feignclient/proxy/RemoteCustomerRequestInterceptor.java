package fr.exalt.businessmicroserviceaccount.infrastructure.adapters.input.feignclient.proxy;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*** this class is an implementation of request interceptor between microservices that communicate between each other*/
public class RemoteCustomerRequestInterceptor implements RequestInterceptor {
    private static final String REGEX = "^Bearer ([a-zA-Z0-9-._~+/]+=*)$";
    private static final Pattern BEARER_TOKEN_HEADER_PATTERN =
            Pattern.compile(REGEX, Pattern.CASE_INSENSITIVE);
    private static final String AUTHORIZATION = HttpHeaders.AUTHORIZATION;
    private final Logger logger = Logger.getLogger(this.getClass().getSimpleName());

    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (Objects.nonNull(requestAttributes)) {
            String authorizationHeader = requestAttributes.getRequest().getHeader(AUTHORIZATION);
            Matcher matcher = BEARER_TOKEN_HEADER_PATTERN.matcher(authorizationHeader);
            if (matcher.matches()) {
                requestTemplate.header(AUTHORIZATION, authorizationHeader);
            } else {
                logger.log(Level.SEVERE,"[BANK-ACCOUNT-BS-MICROSERVICE] authorization not match");
            }
        } else
            logger.log(Level.SEVERE,"[BANK-ACCOUNT-BS-MICROSERVICE] Request Attributes null");
    }
}
