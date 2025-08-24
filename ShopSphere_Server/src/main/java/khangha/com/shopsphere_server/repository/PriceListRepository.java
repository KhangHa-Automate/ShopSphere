package khangha.com.shopsphere_server.repository;

import khangha.com.shopsphere_server.model.entity.PriceList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PriceListRepository extends JpaRepository<PriceList, Long> {
    Optional<PriceList> findByCode(String code);
    
    List<PriceList> findByIsActiveTrue();
    
    List<PriceList> findByCurrencyAndIsActiveTrue(String currency);
    
    List<PriceList> findByStartsAtBeforeAndEndsAtAfterAndIsActiveTrue(OffsetDateTime now, OffsetDateTime now2);
    
    boolean existsByCode(String code);
}
