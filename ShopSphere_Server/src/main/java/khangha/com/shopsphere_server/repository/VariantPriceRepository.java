package khangha.com.shopsphere_server.repository;

import khangha.com.shopsphere_server.model.entity.VariantPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VariantPriceRepository extends JpaRepository<VariantPrice, Long> {
    List<VariantPrice> findByVariantId(Long variantId);
    
    Optional<VariantPrice> findByVariantIdAndPriceListId(Long variantId, Long priceListId);
    
    List<VariantPrice> findByPriceListId(Long priceListId);
}
