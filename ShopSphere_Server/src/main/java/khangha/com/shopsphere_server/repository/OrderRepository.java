package khangha.com.shopsphere_server.repository;

import khangha.com.shopsphere_server.model.entity.Order;
import khangha.com.shopsphere_server.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByOrderNumber(String orderNumber);
    
    List<Order> findByCustomerIdOrderByCreatedAtDesc(UUID customerId);
    
    List<Order> findByStatus(OrderStatus status);
    
    List<Order> findByCustomerIdAndStatus(UUID customerId, OrderStatus status);
    
    @Query("SELECT o FROM Order o WHERE o.createdAt >= :startDate AND o.createdAt <= :endDate")
    List<Order> findByDateRange(@Param("startDate") OffsetDateTime startDate, 
                                @Param("endDate") OffsetDateTime endDate);
    
    boolean existsByOrderNumber(String orderNumber);
}
