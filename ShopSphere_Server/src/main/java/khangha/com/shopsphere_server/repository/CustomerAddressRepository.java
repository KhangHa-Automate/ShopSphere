package khangha.com.shopsphere_server.repository;

import khangha.com.shopsphere_server.model.entity.CustomerAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerAddressRepository extends JpaRepository<CustomerAddress, Long> {
    List<CustomerAddress> findByCustomerId(UUID customerId);
    
    Optional<CustomerAddress> findByCustomerIdAndIsDefaultTrue(UUID customerId);
    
    List<CustomerAddress> findByCustomerIdOrderByIsDefaultDesc(UUID customerId);
}
