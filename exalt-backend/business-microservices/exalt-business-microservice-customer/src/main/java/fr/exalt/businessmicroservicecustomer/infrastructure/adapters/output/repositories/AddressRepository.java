package fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.repositories;

import fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.models.entities.AddressModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AddressRepository extends JpaRepository<AddressModel, String> {
    @Query(value = "select * from addresses a where a.street_num=:num and a.street_name=:street and a.po_box=:poBox and a.city=:city" +
            " and a.country=:country", nativeQuery = true)
    AddressModel findByAddressInfo(@Param("num") int num, @Param("street") String street,
            @Param("poBox") int poBox, @Param("city") String city, @Param("country") String country);
}
