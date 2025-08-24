package khangha.com.shopsphere_server.repository;

import khangha.com.shopsphere_server.model.entity.ShipmentItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShipmentItemRepository extends JpaRepository<ShipmentItem, Long> {
    List<ShipmentItem> findByShipmentId(Long shipmentId);
    
    List<ShipmentItem> findByOrderItemId(Long orderItemId);
}
