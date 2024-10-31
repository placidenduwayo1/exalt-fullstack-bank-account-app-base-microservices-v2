package fr.exalt.businessmicroservicecustomer.domain.ports.input;

import fr.exalt.businessmicroservicecustomer.domain.entities.Address;
import fr.exalt.businessmicroservicecustomer.domain.entities.Customer;
import fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.models.dtos.AddressDto;
import fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.models.dtos.CustomerSwitchStateDto;
import fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.models.dtos.RequestDto;

import java.util.Collection;

public interface InputCustomerService {
    Customer createCustomer(RequestDto requestDto);

    Collection<Customer> getAllCustomers();

    Customer getCustomer(String customerId) ;

    Address getAddress(int num, String street, int poBox,String city, String country) ;
    Address getAddress(String addressId) ;

    Collection<Address> getAllAddresses();

    Customer updateCustomer(String customerId, RequestDto dto) ;

    Address updateAddress(String addressId, AddressDto addressDto) ;

    Customer switchCustomerState(CustomerSwitchStateDto dto);

    Collection<Customer> getAllArchivedCustomer();
}
