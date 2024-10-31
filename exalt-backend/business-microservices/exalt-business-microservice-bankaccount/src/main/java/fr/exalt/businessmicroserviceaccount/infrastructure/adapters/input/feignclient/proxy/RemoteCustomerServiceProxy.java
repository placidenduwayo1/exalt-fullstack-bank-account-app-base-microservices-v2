package fr.exalt.businessmicroserviceaccount.infrastructure.adapters.input.feignclient.proxy;

import fr.exalt.businessmicroserviceaccount.domain.finalvalues.FinalValues;
import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.input.feignclient.models.CustomerModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.logging.Level;
import java.util.logging.Logger;

@FeignClient(name = "exalt-business-microservice-customer",
        path = "/api-customer",
        configuration = RemoteCustomerRequestInterceptor.class,
        fallback = RemoteCustomerServiceProxy.RemoteCustomerFallback.class
        )
@Qualifier(value = "remoteCustomerService")
public interface RemoteCustomerServiceProxy {
    @GetMapping(value = "/customers/{customerId}")
    CustomerModel loadRemoteCustomer(@PathVariable(name = "customerId") String customerId);

    /*** following class is an implementation of fallback if remote customer is unreachable using resilience 4j*/
    @Component
    class RemoteCustomerFallback implements RemoteCustomerServiceProxy {
        private final Logger logger = Logger.getLogger(this.getClass().getSimpleName());
        @Override
        public CustomerModel loadRemoteCustomer(String customerId) {
            CustomerModel resilience = CustomerModel.builder()
                    .customerId(FinalValues.REMOTE_CUSTOMER_API)
                    .firstname(FinalValues.REMOTE_CUSTOMER_API)
                    .lastname(FinalValues.REMOTE_CUSTOMER_API)
                    .email(FinalValues.REMOTE_CUSTOMER_API)
                    .state(FinalValues.REMOTE_CUSTOMER_API)
                    .build();
            logger.log(Level.INFO, "[Fallback] resilience management {0}", resilience);
            return resilience;
        }
    }
}
