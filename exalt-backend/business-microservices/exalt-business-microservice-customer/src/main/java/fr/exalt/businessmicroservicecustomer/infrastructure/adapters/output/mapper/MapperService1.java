package fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.mapper;

import fr.exalt.businessmicroservicecustomer.domain.avrobeans.AddressAvro;
import fr.exalt.businessmicroservicecustomer.domain.avrobeans.CustomerAvro;
import fr.exalt.businessmicroservicecustomer.domain.entities.Address;
import fr.exalt.businessmicroservicecustomer.domain.entities.Customer;
import fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.models.dtos.AddressDto;
import fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.models.entities.AddressModel;
import fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.models.dtos.CustomerDto;
import fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.models.entities.CustomerModel;
import org.springframework.beans.BeanUtils;

public class MapperService1 {
    private MapperService1() {}
    public static AddressModel fromTo(Address address){
        AddressModel model = AddressModel.builder().build();
        BeanUtils.copyProperties(address,model);
        return model;
    }
    public static CustomerModel fromTo(Customer customer){
        CustomerModel model = CustomerModel.builder().build();
        BeanUtils.copyProperties(customer,model);
        return model;
    }

    public static Address fromTo(AddressModel model){
        Address address = Address.builder().build();
        BeanUtils.copyProperties(model,address);
        return address;
    }
    public static Customer fromTo(CustomerModel model){
        Customer customer = Customer.builder().build();
        BeanUtils.copyProperties(model,customer);
        return customer;
    }
    public static CustomerAvro fromCustomer(Customer customer){
        AddressAvro addressAvro = AddressAvro.newBuilder()
                .setAddressId(customer.getAddress().getAddressId())
                .setStreetNum(customer.getAddress().getStreetNum())
                .setStreetName(customer.getAddress().getStreetName())
                .setPoBox(customer.getAddress().getPoBox())
                .setCountry(customer.getAddress().getCountry())
                .setCity(customer.getAddress().getCity())
                .build();
        return CustomerAvro.newBuilder()
                .setCustomerId(customer.getCustomerId())
                .setFirstname(customer.getFirstname())
                .setLastname(customer.getLastname())
                .setEmail(customer.getEmail())
                .setState(customer.getState())
                .setCreatedAt(customer.getCreatedAt())
                .setAddress(addressAvro)
                .build();
    }

    public static Customer fromCustomerAvro(CustomerAvro avro) {
        Address address = Address.builder()
                .addressId(avro.getAddress().getAddressId())
                .streetNum(avro.getAddress().getStreetNum())
                .streetName(avro.getAddress().getStreetName())
                .poBox(avro.getAddress().getPoBox())
                .city(avro.getAddress().getCity())
                .country(avro.getAddress().getCountry())
                .build();
       return Customer.builder()
                .customerId(avro.getCustomerId())
                .firstname(avro.getFirstname())
                .lastname(avro.getLastname())
                .email(avro.getEmail())
                .state(avro.getState())
                .createdAt(avro.getCreatedAt())
                .address(address)
                .build();
    }

    public static Address fromTo(AddressDto addressDto) {
        Address address = Address.builder().build();
        BeanUtils.copyProperties(addressDto,address);
        return address;
    }

    public static Customer fromTo(CustomerDto customerDto) {
        Customer customer = Customer.builder().build();
        BeanUtils.copyProperties(customerDto,customer);
        return customer;
    }
}
