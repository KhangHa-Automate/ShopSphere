package khangha.com.shopsphere_server.repository;

import khangha.com.shopsphere_server.model.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
    Optional<Stock> findByWarehouseIdAndVariantId(Long warehouseId, Long variantId);
    
    List<Stock> findByVariantId(Long variantId);
    
    List<Stock> findByWarehouseId(Long warehouseId);
    
    @Query("SELECT s FROM Stock s WHERE s.variantId = :variantId AND s.qtyOnHand > 0")
    List<Stock> findAvailableStockByVariantId(@Param("variantId") Long variantId);
    
    @Query("SELECT SUM(s.qtyOnHand) FROM Stock s WHERE s.variantId = :variantId")
    Integer getTotalStockByVariantId(@Param("variantId") Long variantId);
}
