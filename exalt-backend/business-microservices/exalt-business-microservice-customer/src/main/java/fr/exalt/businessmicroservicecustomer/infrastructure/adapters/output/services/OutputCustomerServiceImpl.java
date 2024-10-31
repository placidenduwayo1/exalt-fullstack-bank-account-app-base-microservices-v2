package fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.services;

import fr.exalt.businessmicroservicecustomer.domain.avrobeans.CustomerAvro;
import fr.exalt.businessmicroservicecustomer.domain.entities.Address;
import fr.exalt.businessmicroservicecustomer.domain.entities.Customer;
import fr.exalt.businessmicroservicecustomer.domain.exceptions.AddressNotFoundException;
import fr.exalt.businessmicroservicecustomer.domain.exceptions.CustomerNotFoundException;
import fr.exalt.businessmicroservicecustomer.domain.finalvalues.FinalValues;
import fr.exalt.businessmicroservicecustomer.domain.ports.output.OutputCustomerService;
import fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.mapper.MapperService1;
import fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.models.dtos.CustomerDto;
import fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.models.entities.AddressModel;
import fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.models.entities.CustomerModel;
import fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.models.entities.Request;
import fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.repositories.AddressRepository;
import fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.repositories.CustomerRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@Transactional
@Slf4j
public class OutputCustomerServiceImpl implements OutputCustomerService {
    private final CustomerRepository customerRepository;
    private final AddressRepository addressRepository;

    public OutputCustomerServiceImpl(CustomerRepository customerRepository, AddressRepository addressRepository) {
        this.customerRepository = customerRepository;
        this.addressRepository = addressRepository;
    }

    private Request getRequest(CustomerAvro avro) {
        Customer customer = MapperService1.fromCustomerAvro(avro);
        Address address = customer.getAddress();
        AddressModel savedAddress = addressRepository.save(MapperService1.fromTo(address));
        CustomerModel customerModel = customerRepository.save(MapperService1.fromTo(customer));
        customerModel.setAddress(savedAddress);

        return Request.builder()
                .address(MapperService1.fromTo(savedAddress))
                .customer(MapperService1.fromTo(customerModel))
                .build();
    }

    @Override
    //kafka consumer definition
    @KafkaListener(groupId = "group1", autoStartup = "true",
            topicPartitions = @TopicPartition(topic = "customer",
                    partitionOffsets = @PartitionOffset(partition = "0", initialOffset = "0")))
    public Request createCustomerKafkaConsumer(@Payload CustomerAvro avro) {
        log.error("customer with address to create in db {}", avro);
        return getRequest(avro);
    }

    @Override
    public Address getAddress(int num, String street, int poBox, String city, String country) {
        AddressModel model = addressRepository.findByAddressInfo(num, street, poBox, city, country);
        if (model != null) {
            return MapperService1.fromTo(model);
        }
        return null;
    }

    @Override
    public Address getAddress(String addressId) {
        AddressModel model = addressRepository.findById(addressId)
                .orElseThrow(() -> new AddressNotFoundException(FinalValues.ADDRESS_NOT_FOUND));
        return MapperService1.fromTo(model);

    }

    @Override
    public Collection<Customer> getAllCustomers() {
        return setCustomerAddress(customerRepository.findAllCustomer());
    }

    @Override
    public Customer getCustomer(String customerId) throws CustomerNotFoundException {
        CustomerModel model = customerRepository.findById(customerId).orElseThrow(
                () -> new CustomerNotFoundException(FinalValues.CUSTOMER_NOT_FOUND));
        Address address = MapperService1.fromTo(model.getAddress());
        Customer customer = MapperService1.fromTo(model);
        customer.setAddress(address);

        return customer;
    }

    @Override
    public Address createAddress(Address address) {
        AddressModel model = addressRepository.save(MapperService1.fromTo(address));
        return MapperService1.fromTo(model);
    }

    @Override
    public Customer getCustomer(CustomerDto dto) {
        CustomerModel model = customerRepository.findByCustomerInfo(
                dto.getFirstname(), dto.getLastname(), dto.getEmail());
        if (model != null) {
            return MapperService1.fromTo(model);
        }
        return null;
    }

    @Override
    public Collection<Address> getAllAddresses() {
        return addressRepository.findAll().stream()
                .map(MapperService1::fromTo).toList();
    }

    @Override
    //kafka consumer
    @KafkaListener(groupId = "group1", autoStartup = "true",
            topicPartitions = @TopicPartition(topic = "customer",
                    partitionOffsets = @PartitionOffset(partition = "1", initialOffset = "0")))
    public Request updateCustomerKafkaConsumer(@Payload CustomerAvro avro) {
        log.info("customer with address to update and save in db {}", avro);
        return getRequest(avro);
    }

    @Override
    public Address updateAddress(Address address) {
        AddressModel addressModel = addressRepository.save(MapperService1.fromTo(address));
        return MapperService1.fromTo(addressModel);
    }

    @Override
    //kafka consumer definition
    @KafkaListener(groupId = "group1", autoStartup = "true",
            topicPartitions = @TopicPartition(topic = "customer",
                    partitionOffsets = @PartitionOffset(partition = "1",initialOffset = "0")))
    public Customer switchCustomerStateKafkaConsumer(@Payload CustomerAvro avro) {
        log.info("customer to switch state and save in db {}",avro);
        Customer customer = MapperService1.fromCustomerAvro(avro);
        CustomerModel savedCustomer = customerRepository.findById(customer.getCustomerId()).orElseThrow(
                () -> new CustomerNotFoundException(FinalValues.CUSTOMER_NOT_FOUND));
        AddressModel savedAddress = savedCustomer.getAddress();
        CustomerModel model = customerRepository.save(MapperService1.fromTo(customer));
        model.setAddress(savedAddress);
        return MapperService1.fromTo(model);
    }

    @Override
    public Collection<Customer> getAllArchivedCustomer() {
        return setCustomerAddress(customerRepository.findAllArchivedCustomers());
    }

    private Collection<Customer> setCustomerAddress(Collection<CustomerModel> models) {
        return models.stream()
                .map(model -> {
                    Address address = MapperService1.fromTo(model.getAddress());
                    Customer customer = MapperService1.fromTo(model);
                    customer.setAddress(address);
                    return customer;
                })
                .toList();
    }
}
