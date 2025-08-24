package khangha.com.shopsphere_server.repository;

import khangha.com.shopsphere_server.model.entity.Promotion;
import khangha.com.shopsphere_server.enums.PromotionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Long> {
    List<Promotion> findByType(PromotionType type);
    
    List<Promotion> findByStartsAtBeforeAndEndsAtAfter(OffsetDateTime now, OffsetDateTime now2);
}

