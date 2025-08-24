package khangha.com.shopsphere_server.repository;

import khangha.com.shopsphere_server.model.entity.Refund;
import khangha.com.shopsphere_server.enums.RefundStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RefundRepository extends JpaRepository<Refund, Long> {
    List<Refund> findByPaymentId(Long paymentId);
    
    List<Refund> findByStatus(RefundStatus status);
}
