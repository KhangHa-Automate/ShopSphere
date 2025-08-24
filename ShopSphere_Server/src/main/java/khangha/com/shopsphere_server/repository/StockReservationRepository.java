package khangha.com.shopsphere_server.repository;

import khangha.com.shopsphere_server.model.entity.StockReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface StockReservationRepository extends JpaRepository<StockReservation, Long> {
    List<StockReservation> findByVariantIdAndWarehouseId(Long variantId, Long warehouseId);
    
    List<StockReservation> findByRefTypeAndRefId(String refType, Long refId);
    
    List<StockReservation> findByExpiresAtBefore(OffsetDateTime now);
}
