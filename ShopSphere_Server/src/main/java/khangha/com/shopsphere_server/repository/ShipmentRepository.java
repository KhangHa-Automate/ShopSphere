package khangha.com.shopsphere_server.repository;

import khangha.com.shopsphere_server.model.entity.Shipment;
import khangha.com.shopsphere_server.enums.ShipmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, Long> {
    List<Shipment> findByOrderId(Long orderId);
    
    List<Shipment> findByStatus(ShipmentStatus status);
    
    Optional<Shipment> findByTrackingNo(String trackingNo);
    
    List<Shipment> findByCarrier(String carrier);
}
