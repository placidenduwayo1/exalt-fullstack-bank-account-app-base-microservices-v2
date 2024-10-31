package fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.repositories;

import fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.models.entities.CustomerModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface CustomerRepository extends JpaRepository<CustomerModel, String> {
    @Query(value = "select * from customers c where c.firstname=:firstname and c.lastname=:lastname and c.email=:email", nativeQuery = true)
    CustomerModel findByCustomerInfo(@Param("firstname") String firstname,@Param("lastname") String lastname, @Param("email") String email);
    @Query(value = "select * from customers", nativeQuery = true)
    Collection<CustomerModel> findAllCustomer();
    @Query(value = "select * from customers where customers.state='archive'", nativeQuery = true)
    Collection<CustomerModel> findAllArchivedCustomers();
}
