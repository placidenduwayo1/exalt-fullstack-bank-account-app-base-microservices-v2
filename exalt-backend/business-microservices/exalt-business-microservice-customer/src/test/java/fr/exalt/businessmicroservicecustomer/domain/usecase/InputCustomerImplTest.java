package fr.exalt.businessmicroservicecustomer.domain.usecase;

import fr.exalt.businessmicroservicecustomer.domain.avrobeans.CustomerAvro;
import fr.exalt.businessmicroservicecustomer.domain.entities.Address;
import fr.exalt.businessmicroservicecustomer.domain.entities.Customer;
import fr.exalt.businessmicroservicecustomer.domain.exceptions.*;
import fr.exalt.businessmicroservicecustomer.domain.ports.output.OutputCustomerService;
import fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.mapper.MapperService1;
import fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.models.dtos.AddressDto;
import fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.models.dtos.CustomerDto;
import fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.models.dtos.CustomerSwitchStateDto;
import fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.models.dtos.RequestDto;
import fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.models.entities.Request;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Slf4j
class InputCustomerImplTest {

    @Mock
    private OutputCustomerService mock1;
    @InjectMocks
    private InputCustomerImpl underTest;
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
    private final Customer customer = MapperService1.fromTo(customerDto);
    private final Address address = MapperService1.fromTo(addressDto);
    private final RequestDto requestDto = RequestDto.builder()
            .addressDto(addressDto)
            .customerDto(customerDto)
            .build();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        customer.setCustomerId("1");
        customer.setState("active");
        customer.setCreatedAt("now");
        address.setAddressId("1");
        customer.setAddress(address);
    }

    @Test
    void createCustomer(){
        // PREPARE
        final Request request = Request.builder()
                .address(address)
                .customer(customer)
                .build();
        //EXECUTE
        when(mock1.createAddress(any(Address.class))).thenReturn(address);
        when(mock1.createCustomerKafkaConsumer(any(CustomerAvro.class)))
                .thenReturn(request);
        final Customer actual = underTest.createCustomer(requestDto);
        //VERIFY
        assertAll("", () -> {
            verify(mock1, atLeast(1)).createAddress(any(Address.class));
            assertNotNull(actual);
        });
    }

    @Test
    void getAllCustomers() {
        //PREPARE
        final Collection<Customer> customers = List.of(customer);
        //EXECUTE
        when(mock1.getAllCustomers()).thenReturn(customers);
        Collection<Customer> actual = underTest.getAllCustomers();
        //VERIFY
        assertAll("", () -> {
            verify(mock1, atLeast(1)).getAllCustomers();
            assertEquals(1, actual.size());
        });
    }

    @Test
    void getCustomer(){
        //PREPARE
        final String id = "1";
        //EXECUTE
        when(mock1.getCustomer(id)).thenReturn(customer);
        Customer actual = underTest.getCustomer(id);
        //VERIFY
        assertAll("", () -> {
            verify(mock1, atLeast(1)).getCustomer(id);
            assertNotNull(actual);
        });
    }

    @Test
    void getAddress(){
        //PREPARE
        final String id = "1";
        //EXECUTE
        when(mock1.getAddress(id)).thenReturn(address);
        final Address actual = underTest.getAddress(id);
        //VERIFY
        assertAll("", () -> {
            verify(mock1, atLeast(1)).getAddress(id);
            assertNotNull(actual);
        });


    }

    @Test
    void testGetAddress() {
        //PREPARE
        final Address newAddress = new Address.AddressBuilder()
                .addressId("1")
                .streetNum(184)
                .streetName("avenue de liège")
                .build();
        //EXECUTE
        when(mock1.getAddress(184,"avenue de liege",59300,"valenciennes","france")).thenReturn(address);
        Address actual = underTest.getAddress(184,"avenue de liege",59300,"valenciennes","france");
        //VERIFY
        assertAll("", () -> {
            verify(mock1, atLeast(1)).getAddress(184,"avenue de liege",59300,"valenciennes","france");
            assertNotNull(actual);
            assertNotEquals(newAddress.toString(), address.toString());
        });
    }

    @Test
    void getAllAddresses() {
        //PREPARE
        final Collection<Address> addresses = List.of(address);
        //EXECUTE
        when(mock1.getAllAddresses()).thenReturn(addresses);
        Collection<Address> actual = underTest.getAllAddresses();
        //VERIFY
        assertAll("", () -> {
            verify(mock1, atLeast(1)).getAllAddresses();
            assertNotEquals(2, actual.size());
            assertFalse(actual.isEmpty());
        });
    }

   @Test
    void updateCustomer() {
        //PREPARE
        final String id = "1";
        final RequestDto dto = RequestDto.builder()
                .customerDto(customerDto)
                .addressDto(addressDto)
                .build();
        final Request request = Request.builder()
                .customer(customer)
                .address(address)
                .build();
        //EXECUTE
        when(mock1.getCustomer(id)).thenReturn(customer);
        when(mock1.createAddress(any(Address.class))).thenReturn(address);
        when(mock1.updateCustomerKafkaConsumer(any(CustomerAvro.class)))
                .thenReturn(request);
        Customer actual = underTest.updateCustomer(id, dto);
        //VERIFY
        assertAll("", () -> {
            verify(mock1, atLeast(1)).getCustomer(id);
            assertNotNull(actual);
        });
    }

    @Test
    void updateAddress(){
        //PREPARE
        final String id = "1";
        //EXECUTE
        when(mock1.getAddress(id)).thenReturn(address);
        when(mock1.updateAddress(any(Address.class))).thenReturn(address);
        Address actual = underTest.updateAddress(id, addressDto);
        //VERIFY
        assertAll("", () -> {
            verify(mock1, atLeast(1)).getAddress(id);
            verify(mock1, atLeast(1)).updateAddress(address);
            assertNotNull(actual);
        });
    }

    @Test
    void archiveCustomer(){
        //PREPARE
        final CustomerSwitchStateDto dto = CustomerSwitchStateDto.builder()
                .customerId("id")
                .state("archive")
                .build();
        final Customer c = new Customer.CustomerBuilder()
                .customerId(customer.getCustomerId())
                .firstname(customer.getFirstname())
                .lastname(customer.getLastname())
                .createdAt(customer.getCreatedAt())
                .state("archive")
                .address(address)
                .email(customer.getEmail())
                .build();
        //EXECUTE
        when(mock1.getAddress("1")).thenReturn(address);
        when(mock1.getCustomer("id")).thenReturn(customer);
        when(mock1.switchCustomerStateKafkaConsumer(any(CustomerAvro.class))).thenReturn(c);
        Customer actual = underTest.switchCustomerState(dto);
        //VERIFY
        assertAll("", () -> {
            verify(mock1, atLeast(1)).getCustomer("id");
            assertNotNull(actual);
            assertEquals("archive", actual.getState());
        });
    }

    @Test
    void testCustomerBusinessExceptions() {
        final RequestDto dto1 = RequestDto.builder()
                .addressDto(addressDto)
                .customerDto(customerDto)
                .build();
        dto1.getCustomerDto().setEmail("");
        RuntimeException exception1 = assertThrows(CustomerOneOrMoreFieldsInvalidException.class, () ->
                underTest.createCustomer(dto1));

        final RequestDto dto3 = RequestDto.builder()
                .addressDto(addressDto)
                .customerDto(CustomerDto.builder()
                        .firstname(FIRSTNAME)
                        .lastname(LASTNAME)
                        .email(EMAIL)
                        .build())
                .build();

        RuntimeException exception3 = assertThrows(CustomerAlreadyExistsException.class, () -> {
            when(mock1.getCustomer(dto3.getCustomerDto())).thenReturn(customer);
            underTest.createCustomer(dto3);
        });

        final RequestDto dto4 = RequestDto.builder()
                .addressDto(AddressDto.builder()
                        .streetNum(-1)
                        .streetName("")
                        .poBox(-1)
                        .city("")
                        .country("")
                        .build())
                .customerDto(customerDto)
                .build();

        RuntimeException exception4 = assertThrows(AddressFieldsInvalidException.class, () -> {
            when(mock1.getAddress(address.getAddressId())).thenReturn(address);
            underTest.updateAddress(address.getAddressId(), dto4.getAddressDto());
        });


        final CustomerSwitchStateDto dto5 = CustomerSwitchStateDto.builder()
                .state("unknown")
                .customerId("id")
                .build();
        RuntimeException exception5 = assertThrows(CustomerStateInvalidException.class, () -> {
            when(mock1.getCustomer(dto5.getCustomerId())).thenReturn(customer);
            underTest.switchCustomerState(dto5);
        });

        final Customer customer1 = new Customer.CustomerBuilder()
                .customerId(customer.getCustomerId())
                .firstname(customer.getFirstname())
                .lastname(customer.getLastname())
                .createdAt(customer.getCreatedAt())
                .state("archive")
                .address(customer.getAddress())
                .email(customer.getEmail())
                .build();
        final CustomerSwitchStateDto dto6 = CustomerSwitchStateDto.builder()
                .state("archive")
                .customerId("id")
                .build();
        RuntimeException exception6 = assertThrows(CustomerSameStateException.class, () -> {
            when(mock1.getCustomer(dto6.getCustomerId())).thenReturn(customer1);
            underTest.switchCustomerState(dto6);
        });

        assertAll("exceptions", () -> {
            assertNotNull(exception1);
            assertNotNull(exception3);
            assertNotNull(exception4);
            assertNotNull(exception5);
            assertNotNull(exception6);
        });
    }
}