package fr.exalt.businessmicroservicecustomer.infrastructure.adapters.input;

import fr.exalt.businessmicroservicecustomer.domain.entities.Address;
import fr.exalt.businessmicroservicecustomer.domain.entities.Customer;
import fr.exalt.businessmicroservicecustomer.domain.ports.input.InputCustomerService;
import fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.models.dtos.AddressDto;
import fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.models.dtos.CustomerSwitchStateDto;
import fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.models.dtos.RequestDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(value = "/api-customer")
@AllArgsConstructor
public class CustomerController {
    private final InputCustomerService inputCustomerService;

    @PostMapping(value = "/customers")
    public ResponseEntity<Customer> createCustomer(@RequestBody RequestDto dto){
        return new ResponseEntity<>(inputCustomerService.createCustomer(dto), HttpStatus.OK);
    }

    @GetMapping(value = "/customers")
    public ResponseEntity<Collection<Customer>> getAllCustomers() {
        return new ResponseEntity<>(inputCustomerService.getAllCustomers(), HttpStatus.OK);
    }

    @GetMapping(value = "/addresses")
    public ResponseEntity<Collection<Address>> getAllAddresses() {
        return new ResponseEntity<>(inputCustomerService.getAllAddresses(), HttpStatus.OK);
    }

    @PutMapping(value = "/customers/{customerId}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable(name = "customerId") String customerId, @RequestBody RequestDto dto){
        return new ResponseEntity<>(inputCustomerService.updateCustomer(customerId, dto), HttpStatus.OK);
    }

    @PutMapping(value = "/addresses/{addressId}")
    public ResponseEntity<Address> updateAddress(@PathVariable(name = "addressId") String addressId, @RequestBody AddressDto addressDto){
        return new ResponseEntity<>(inputCustomerService.updateAddress(addressId, addressDto), HttpStatus.OK);
    }

    @GetMapping(value = "/customers/{customerId}")
    public Customer getCustomer(@PathVariable(name = "customerId") String customerId) {
        return inputCustomerService.getCustomer(customerId);
    }

    @PostMapping(value = "/customers/switch-state")
    public ResponseEntity<Customer> switchCustomerBetweenActiveArchive(@RequestBody CustomerSwitchStateDto dto){
        return new ResponseEntity<>(inputCustomerService.switchCustomerState(dto), HttpStatus.OK);
    }
    @GetMapping(value = "/customers/archived")
    public ResponseEntity<Collection<Customer>> getAllArchivedCustomer(){
        return new ResponseEntity<>(inputCustomerService.getAllArchivedCustomer(), HttpStatus.OK);
    }
}
