package khangha.com.shopsphere_server.repository;

import khangha.com.shopsphere_server.model.entity.PaymentIntent;
import khangha.com.shopsphere_server.enums.PaymentIntentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentIntentRepository extends JpaRepository<PaymentIntent, Long> {
    List<PaymentIntent> findByOrderId(Long orderId);
    
    List<PaymentIntent> findByStatus(PaymentIntentStatus status);
    
    Optional<PaymentIntent> findByIdempotencyKey(String idempotencyKey);
}
