package khangha.com.shopsphere_server.repository;

import khangha.com.shopsphere_server.model.entity.PromotionCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PromotionCodeRepository extends JpaRepository<PromotionCode, Long> {
    Optional<PromotionCode> findByCodeAndIsActiveTrue(String code);
    
    boolean existsByCode(String code);
}

