package khangha.com.shopsphere_server.repository;

import khangha.com.shopsphere_server.model.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
    List<ProductImage> findByProductIdOrderBySortOrderAsc(Long productId);
    
    List<ProductImage> findByVariantIdOrderBySortOrderAsc(Long variantId);
    
    List<ProductImage> findByProductIdAndVariantIdIsNullOrderBySortOrderAsc(Long productId);
}
