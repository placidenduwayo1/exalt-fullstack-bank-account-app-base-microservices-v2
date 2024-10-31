package fr.exalt.businessmicroservicecustomer.domain.ports.output;

import fr.exalt.businessmicroservicecustomer.domain.avrobeans.CustomerAvro;
import fr.exalt.businessmicroservicecustomer.domain.entities.Address;
import fr.exalt.businessmicroservicecustomer.domain.entities.Customer;
import fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.models.dtos.CustomerDto;
import fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.models.entities.Request;

import java.util.Collection;

public interface OutputCustomerService {
    Request createCustomerKafkaConsumer(CustomerAvro avro);
   Address getAddress(int num, String street, int poBox,String city, String country);
    Address getAddress(String addressId);
    Collection<Customer> getAllCustomers();
    Customer getCustomer(String customerId);
    Address createAddress(Address address);
    Customer getCustomer(CustomerDto dto);
    Collection<Address> getAllAddresses();
    Request updateCustomerKafkaConsumer(CustomerAvro avro);
    Address updateAddress(Address address);
    Customer switchCustomerStateKafkaConsumer(CustomerAvro avro);

    Collection<Customer> getAllArchivedCustomer();
}
