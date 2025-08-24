package khangha.com.shopsphere_server.repository;

import khangha.com.shopsphere_server.model.entity.Cart;
import khangha.com.shopsphere_server.enums.CartStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByCustomerIdAndStatus(UUID customerId, CartStatus status);
    
    List<Cart> findByCustomerId(UUID customerId);
    
    List<Cart> findByStatus(CartStatus status);
}
