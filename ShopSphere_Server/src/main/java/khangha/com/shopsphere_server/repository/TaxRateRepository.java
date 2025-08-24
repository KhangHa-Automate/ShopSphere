package khangha.com.shopsphere_server.repository;

import khangha.com.shopsphere_server.model.entity.TaxRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface TaxRateRepository extends JpaRepository<TaxRate, Long> {
    List<TaxRate> findByCountryCode(String countryCode);
    
    List<TaxRate> findByCountryCodeAndRegion(String countryCode, String region);
    
    List<TaxRate> findByStartsAtBeforeAndEndsAtAfter(OffsetDateTime now, OffsetDateTime now2);
}
