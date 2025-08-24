package khangha.com.shopsphere_server.repository;

import khangha.com.shopsphere_server.model.entity.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductVariantRepository extends JpaRepository<ProductVariant, Long> {
    Optional<ProductVariant> findBySku(String sku);
    
    List<ProductVariant> findByProductIdAndIsActiveTrue(Long productId);
    
    List<ProductVariant> findByProductId(Long productId);
    
    boolean existsBySku(String sku);
}
