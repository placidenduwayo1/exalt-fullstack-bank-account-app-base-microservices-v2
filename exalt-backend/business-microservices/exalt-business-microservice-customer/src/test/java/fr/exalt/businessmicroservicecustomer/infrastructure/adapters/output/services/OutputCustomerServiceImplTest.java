package fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.services;

import fr.exalt.businessmicroservicecustomer.domain.entities.Address;
import fr.exalt.businessmicroservicecustomer.domain.entities.Customer;
import fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.mapper.MapperService1;
import fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.models.dtos.AddressDto;
import fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.models.dtos.CustomerDto;
import fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.models.entities.AddressModel;
import fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.models.entities.CustomerModel;
import fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.models.entities.Request;
import fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.repositories.AddressRepository;
import fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


class OutputCustomerServiceImplTest {
    @Mock
    private CustomerRepository mock1;
    @Mock
    private AddressRepository mock2;
    @InjectMocks
    private OutputCustomerServiceImpl underTest;
    private static final String CUSTOMER_ID = "1";
    private static final String FIRSTNAME = "placide";
    private static final String LASTNAME = "nduwayo";
    private static final String STATE = "active";
    private static final String EMAIL = "placide.nd@gmail.com";
    private final CustomerModel customerModel = CustomerModel.builder()
            .customerId(CUSTOMER_ID)
            .firstname(FIRSTNAME)
            .lastname(LASTNAME)
            .email(EMAIL)
            .state(STATE)
            .createdAt("now")
            .build();
    private final AddressModel addressModel = AddressModel.builder()
            .addressId("1")
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
    void createCustomerKafkaConsumer() {
        //PREPARE
        Address address = Address.builder()
                .addressId("1")
                .streetNum(184)
                .streetName("avenue de liège")
                .poBox(59300)
                .city("Valenciennes")
                .country("france")
                .build();
        Customer customer = Customer.builder()
                .state(STATE)
                .customerId(CUSTOMER_ID)
                .createdAt("now")
                .email(EMAIL)
                .firstname(FIRSTNAME)
                .lastname(LASTNAME)
                .address(address)
                .build();

        //EXECUTE
       when(mock2.save(any(AddressModel.class))).thenReturn(addressModel);
       when(mock1.save(any(CustomerModel.class))).thenReturn(customerModel);
       Request actual = underTest.createCustomerKafkaConsumer(MapperService1.fromCustomer(customer));
        //VERIFY
        assertAll("",()->{
           verify(mock2, atLeast(1)).save(any(AddressModel.class));
           verify(mock1, atLeast(1)).save(any(CustomerModel.class));
           assertNotNull(actual);
        });
    }

    @Test
    void getAddress(){
        //PREPARE
        final String id="1";
        //EXECUTE
        when(mock2.findById(id)).thenReturn(Optional.of(addressModel));
        Address actual = underTest.getAddress(id);
        //VERIFY
        assertAll("",()->{
            verify(mock2, atLeast(1)).findById(id);
            assertNotNull(actual);
        });
    }

    @Test
    void testGetAddress() {
        //PREPARE
        AddressDto dto = AddressDto.builder()
                .streetNum(184)
                .streetName("avenue de liège")
                .poBox(59300)
                .city("valenciennes")
                .country("france")
                .build();
        //EXECUTE
        when(mock2.findByAddressInfo(
                dto.getStreetNum(), dto.getStreetName(), dto.getPoBox(), dto.getCity(), dto.getCountry())).thenReturn(addressModel);
        Address actual = underTest.getAddress(dto.getStreetNum(), dto.getStreetName(),
                dto.getPoBox(), dto.getCity(), dto.getCountry());
        //VERIFY
        assertAll("",()->{
            verify(mock2, atLeast(1)).findByAddressInfo(
                    dto.getStreetNum(), dto.getStreetName(), dto.getPoBox(), dto.getCity(), dto.getCountry());
            assertNotNull(actual);
        });
    }

    @Test
    void getAllCustomers() {
        //PREPARE
        List<CustomerModel> models = List.of(customerModel);
        Collection<Customer> customers = models.stream()
                .map(model -> {
                    model.setAddress(addressModel);
                    return model;
                })
                .map(MapperService1::fromTo)
                .toList();
        //EXECUTE
        when(mock1.findAllCustomer()).thenReturn(models);
        Collection<Customer> actual = underTest.getAllCustomers();
        //VERIFY
        assertAll("",()->{
            verify(mock1, atLeast(1)).findAllCustomer();
            assertEquals(customers.size(),actual.size());
            assertFalse(actual.isEmpty());
        });
    }

    @Test
    void getCustomer(){
        //PREPARE
        final String id = "1";
        CustomerModel model = CustomerModel.builder()
                .customerId("1")
                .firstname(FIRSTNAME)
                .lastname(LASTNAME)
                .email(EMAIL)
                .state(STATE)
                .createdAt("now")
                .address(addressModel)
                .build();
        //EXECUTE
        when(mock2.findById(id)).thenReturn(Optional.of(addressModel));
        when(mock1.findById(id)).thenReturn(Optional.of(model));
        Customer actual = underTest.getCustomer(id);
        //VERIFY
        assertAll("",()->{
            verify(mock1, atLeast(1)).findById(id);
            assertNotNull(actual);
        });
    }

    @Test
    void createAddress() {
        //PREPARE
        Address addr = MapperService1.fromTo(addressModel);
        //EXECUTE
        when(mock2.save(any(AddressModel.class))).thenReturn(addressModel);
        Address actual = underTest.createAddress(addr);
        //VERIFY
        assertAll("",()->{
            verify(mock2, atLeast(1)).save(any(AddressModel.class));
            assertNotNull(actual);
            assertEquals(addr.toString(),actual.toString());
        });
    }

    @Test
    void testGetCustomer() {
        //PREPARE
        CustomerDto dto = CustomerDto.builder()
                .firstname("placide")
                .lastname("nduwayo")
                .email("placide.nd@gmail.com")
                .build();
        //EXECUTE
        when(mock1.findByCustomerInfo(
                dto.getFirstname(), dto.getLastname(), dto.getEmail())).thenReturn(customerModel);
        Customer actual = underTest.getCustomer(dto);
        //VERIFY
        assertAll("",()->{
            verify(mock1, atLeast(1)).findByCustomerInfo(
                    dto.getFirstname(), dto.getLastname(), dto.getEmail());
            assertNotNull(actual);
        });
    }

    @Test
    void getAllAddresses() {
        //PREPARE
        List<AddressModel> models = List.of(addressModel);
        //EXECUTE
        when(mock2.findAll()).thenReturn(models);
        Collection<Address> actual = underTest.getAllAddresses();
        //VERIFY
        assertAll("",()->{
            verify(mock2, atLeast(1)).findAll();
            assertFalse(actual.isEmpty());
        });
    }

    @Test
    void updateCustomer() {
        //PREPARE
        Address a = Address.builder()
                .addressId(addressModel.getAddressId())
                .streetNum(addressModel.getStreetNum())
                .streetName(addressModel.getStreetName())
                .poBox(addressModel.getPoBox())
                .city(addressModel.getCity())
                .country(addressModel.getCountry())
                .build();
        Customer c = Customer.builder()
                .customerId(customerModel.getCustomerId())
                .firstname(customerModel.getFirstname())
                .lastname(customerModel.getLastname())
                .email(customerModel.getEmail())
                .state(customerModel.getState())
                .createdAt(customerModel.getCreatedAt())
                .address(a)
                .build();

        //EXECUTE
        when(mock1.save(any(CustomerModel.class))).thenReturn(customerModel);
        when(mock2.save(any(AddressModel.class))).thenReturn(addressModel);
        Request actual = underTest.updateCustomerKafkaConsumer(MapperService1.fromCustomer(c));
        //VERIFY
        assertAll("",()->{
            verify(mock2, atLeast(1)).save(any(AddressModel.class));
            verify(mock1, atLeast(1)).save(any(CustomerModel.class));
            assertNotNull(actual);
        });
    }

    @Test
    void updateAddress() {
        //PREPARE
        Address a = MapperService1.fromTo(addressModel);
        //EXECUTE
        when(mock2.save(any(AddressModel.class))).thenReturn(addressModel);
        Address actual = underTest.updateAddress(a);
        //VERIFY
        assertAll("",()->{
            verify(mock2, atLeast(1)).save(any(AddressModel.class));
            assertNotNull(actual);
        });
    }
}