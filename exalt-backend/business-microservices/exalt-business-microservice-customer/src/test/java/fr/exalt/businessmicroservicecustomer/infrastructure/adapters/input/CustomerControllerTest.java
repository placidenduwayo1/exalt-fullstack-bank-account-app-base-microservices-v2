package fr.exalt.businessmicroservicecustomer.infrastructure.adapters.input;

import fr.exalt.businessmicroservicecustomer.domain.entities.Address;
import fr.exalt.businessmicroservicecustomer.domain.entities.Customer;
import fr.exalt.businessmicroservicecustomer.domain.ports.input.InputCustomerService;
import fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.mapper.MapperService1;
import fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.models.dtos.AddressDto;
import fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.models.dtos.CustomerDto;
import fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.models.dtos.RequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerControllerTest {
    @Mock
    private InputCustomerService mock;
    @InjectMocks
    private CustomerController underTest;
    private static final String FIRSTNAME = "placide";
    private static final String LASTNAME = "nduwayo";
    private static final String EMAIL = "placide.nd@gmail.com";
    private final CustomerDto customerDto = CustomerDto.builder()
            .firstname(FIRSTNAME)
            .lastname(LASTNAME)
            .email(EMAIL)
            .build();
    private final AddressDto addressDto = AddressDto.builder()
            .streetNum(184)
            .streetName("avenue de liège")
            .poBox(59300)
            .city("Valenciennes")
            .country("france")
            .build();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createCustomer(){
        //PREPARE
        RequestDto dto = RequestDto.builder()
                .addressDto(addressDto)
                .customerDto(customerDto)
                .build();
        Customer customer = Customer.builder()
                .customerId("1")
                .firstname(FIRSTNAME)
                .lastname(LASTNAME)
                .email(EMAIL)
                .state("active")
                .createdAt("now")
                .build();
        //EXECUTE
        when(mock.createCustomer(dto)).thenReturn(customer);
        ResponseEntity<Customer> actual = underTest.createCustomer(dto);
        //VERIFY
        assertAll("", () -> {
            verify(mock, atLeast(1)).createCustomer(dto);
            assertNotNull(actual);
            assertEquals(200, actual.getStatusCode().value());
        });
    }

    @Test
    void getAllCustomers() {
        //PREPARE
        Customer customer = Customer.builder()
                .customerId("id")
                .firstname(FIRSTNAME)
                .lastname(LASTNAME)
                .email(EMAIL)
                .state("active")
                .createdAt("now")
                .build();
        Collection<Customer> customers = List.of(customer);
        //EXECUTE
        when(mock.getAllCustomers()).thenReturn(customers);
        ResponseEntity<Collection<Customer> > actual = underTest.getAllCustomers();
        //VERIFY
        assertAll("",()->{
            verify(mock, atLeast(1)).getAllCustomers();
            assertEquals(200, actual.getStatusCode().value());
        });
    }

    @Test
    void getAllAddresses() {
        //PREPARE
        Address address = Address.builder()
                .streetNum(184)
                .streetName("avenue de liège")
                .poBox(59300)
                .city("Valenciennes")
                .country("france")
                .build();
        Collection<Address> addresses = List.of(address);
        //EXECUTE
        when(mock.getAllAddresses()).thenReturn(addresses);
        ResponseEntity<Collection<Address>> actual = underTest.getAllAddresses();
        //VERIFY
        assertAll("",()->{
            verify(mock, atLeast(1)).getAllAddresses();
            assertNotNull(actual);
            assertEquals(200,actual.getStatusCode().value());
        });

    }

    @Test
    void updateCustomer(){
        //PREPARE
        RequestDto dto = RequestDto.builder()
                .addressDto(addressDto)
                .customerDto(customerDto)
                .build();
        Customer customer = MapperService1.fromTo(customerDto);
        final String id ="1";
        //EXECUTE
        when(mock.updateCustomer(id,dto)).thenReturn(customer);
        ResponseEntity<Customer> actual = underTest.updateCustomer(id,dto);
        //VERIFY
        assertAll("", () -> {
            verify(mock, atLeast(1)).updateCustomer(id,dto);
            assertNotNull(actual);
            assertEquals(200, actual.getStatusCode().value());
        });
    }

    @Test
    void updateAddress(){
        //PREPARE
        final String id="1";
        Address address = Address.builder()
                .addressId("address-id")
                .streetNum(184)
                .streetName("avenue de liège")
                .poBox(59300)
                .city("Valenciennes")
                .country("france")
                .build();
        //EXECUTE
        when(mock.updateAddress(id,addressDto)).thenReturn(address);
        ResponseEntity<Address> actual = underTest.updateAddress(id, addressDto);
        //VERIFY
        assertAll("",()->{
            verify(mock, atLeast(1)).updateAddress(id, addressDto);
            assertNotNull(actual);
            assertEquals(200, actual.getStatusCode().value());
        });
    }

    @Test
    void getCustomer(){
        //PREPARE
        final String id = "1";
        Customer customer = Customer.builder()
                .customerId("id")
                .firstname(FIRSTNAME)
                .lastname(LASTNAME)
                .email(EMAIL)
                .state("active")
                .createdAt("now")
                .build();
        //EXECUTE
        when(mock.getCustomer(id)).thenReturn(customer);
        Customer actual = underTest.getCustomer(id);
        //VERIFY
        assertAll("",()->{
            verify(mock, atLeast(1)).getCustomer(id);
            assertNotNull(actual);
        });
    }
}