package khangha.com.shopsphere_server.repository;

import khangha.com.shopsphere_server.model.entity.Payment;
import khangha.com.shopsphere_server.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByOrderId(Long orderId);
    
    List<Payment> findByStatus(PaymentStatus status);
    
    Optional<Payment> findByProviderRef(String providerRef);
    
    List<Payment> findByProvider(String provider);
}
