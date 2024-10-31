package fr.exalt.businessmicroservicecustomer.domain.usecase;

import fr.exalt.businessmicroservicecustomer.domain.entities.Address;
import fr.exalt.businessmicroservicecustomer.domain.entities.Customer;
import fr.exalt.businessmicroservicecustomer.domain.exceptions.*;
import fr.exalt.businessmicroservicecustomer.domain.finalvalues.FinalValues;
import fr.exalt.businessmicroservicecustomer.domain.ports.input.InputCustomerService;
import fr.exalt.businessmicroservicecustomer.domain.ports.output.KafkaProducerService;
import fr.exalt.businessmicroservicecustomer.domain.ports.output.OutputCustomerService;
import fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.mapper.MapperService1;
import fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.models.dtos.AddressDto;
import fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.models.dtos.CustomerSwitchStateDto;
import fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.models.dtos.RequestDto;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collection;
import java.util.UUID;

import static fr.exalt.businessmicroservicecustomer.domain.finalvalues.FinalValues.INITIAL_STATE;

@Slf4j
public class InputCustomerImpl implements InputCustomerService {
    private final OutputCustomerService outputCustomerService;
    private final KafkaProducerService kafkaProducer;

    public InputCustomerImpl(OutputCustomerService outputCustomerService, KafkaProducerService kafkaProducer) {
        this.outputCustomerService = outputCustomerService;
        this.kafkaProducer = kafkaProducer;
    }

    @Override
    public Customer createCustomer(RequestDto requestDto) {

        validateCustomerCreate(requestDto);
        Address mappedAddress = MapperService1.fromTo(requestDto.getAddressDto());
        mappedAddress.setAddressId(UUID.randomUUID().toString());
        Address savedAddress = getAddress(
                requestDto.getAddressDto().getStreetNum(),
                requestDto.getAddressDto().getStreetName(),
                requestDto.getAddressDto().getPoBox(),
                requestDto.getAddressDto().getCity(),
                requestDto.getAddressDto().getCountry());
        Customer customer = MapperService1.fromTo(requestDto.getCustomerDto());
        if (savedAddress == null) {
            savedAddress = outputCustomerService.createAddress(mappedAddress);
            customer.setAddress(savedAddress);
        }

        customer.setAddress(savedAddress);
        customer.setCustomerId(UUID.randomUUID().toString());
        customer.setCreatedAt(Timestamp.from(Instant.now()).toString());
        customer.setState(INITIAL_STATE);
        //call kafka producer to send customer avro to create: topic in partition 0
        kafkaProducer.producerCustomerModelCreateEvent(MapperService1.fromCustomer(customer));
        return customer;
    }

    @Override
    public Collection<Customer> getAllCustomers() {
        return outputCustomerService.getAllCustomers();
    }

    @Override
    public Customer getCustomer(String customerId) {
        return outputCustomerService.getCustomer(customerId);
    }

    @Override
    public Address getAddress(int num, String street, int poBox,String city, String country) {
        return outputCustomerService.getAddress(num,street,poBox,city,country);
    }

    @Override
    public Address getAddress(String addressId)  {
        return outputCustomerService.getAddress(addressId);
    }

    @Override
    public Collection<Address> getAllAddresses() {
        return outputCustomerService.getAllAddresses();
    }

    @Override
    public Customer updateCustomer(String customerId, RequestDto requestDto) {

        validateCustomerUpdate(requestDto);
        Customer customer = getCustomer(customerId);
        Address address = getAddress(requestDto.getAddressDto().getStreetNum(),
                requestDto.getAddressDto().getStreetName(),
                requestDto.getAddressDto().getPoBox(),
                requestDto.getAddressDto().getCity(),
                requestDto.getAddressDto().getCountry());
        if (address == null) {
            address = outputCustomerService.createAddress(MapperService1.fromTo(requestDto.getAddressDto()));
        }
        //field value that won't change
        customer.setCustomerId(customerId);
        //fields value that will change
        customer.setAddress(address);
        customer.setFirstname(requestDto.getCustomerDto().getFirstname());
        customer.setLastname(requestDto.getCustomerDto().getLastname());
        customer.setEmail(requestDto.getCustomerDto().getEmail());
        //call kafka producer to send customer avro to update: topic in partition 1
        kafkaProducer.producerCustomerModelUpdateEvent(MapperService1.fromCustomer(customer));
        return customer;

    }

    @Override
    public Address updateAddress(String addressId, AddressDto dto) {
        CustomerValidators.formatter(dto);
        if (CustomerValidators.invalidAddressDto(dto)) {
            throw new AddressFieldsInvalidException(FinalValues.ADDRESS_FIELDS);
        }
        Address address = getAddress(addressId);
        address.setStreetNum(dto.getStreetNum());
        address.setStreetName(dto.getStreetName());
        address.setPoBox(dto.getPoBox());
        address.setCity(dto.getCity());
        address.setCountry(dto.getCountry());

        // call output adapter to register updated address
        return outputCustomerService.updateAddress(address);
    }

    @Override
    public Customer switchCustomerState(CustomerSwitchStateDto dto) {
        if (!CustomerValidators.isValidCustomerState(dto.getState())) {
            throw new CustomerStateInvalidException(FinalValues.CUSTOMER_STATE_INVALID);
        }
        Customer customer = getCustomer(dto.getCustomerId());
        if (customer.getState().equals(dto.getState()))
            throw new CustomerSameStateException(FinalValues.CUSTOMER_ALREADY_IN_STATE);
        customer.setState(dto.getState());
        customer.setAddress(customer.getAddress());
        //call kafka producer to send customer avro to switch state : topic in partition 1
        kafkaProducer.producerCustomerModelSwitchState(MapperService1.fromCustomer(customer));
        return customer;
    }

    @Override
    public Collection<Customer> getAllArchivedCustomer() {
        return outputCustomerService.getAllArchivedCustomer();
    }


    private void validateCustomerCreate(RequestDto requestDto) {

        CustomerValidators.formatter(requestDto);
        if (CustomerValidators.invalidRequest(requestDto)) {
            throw new CustomerOneOrMoreFieldsInvalidException(FinalValues.CUSTOMER_FIELD_INVALID);
        }


        Customer savedCustomer = outputCustomerService.getCustomer(requestDto.getCustomerDto());
        if (savedCustomer != null) {
            throw new CustomerAlreadyExistsException(FinalValues.CUSTOMER_ALREADY_EXISTS);
        }
    }
    private void validateCustomerUpdate(RequestDto requestDto) {
        CustomerValidators.formatter(requestDto);
        if (CustomerValidators.invalidRequest(requestDto)) {
            throw new CustomerOneOrMoreFieldsInvalidException(FinalValues.CUSTOMER_FIELD_INVALID);
        }
    }
}
