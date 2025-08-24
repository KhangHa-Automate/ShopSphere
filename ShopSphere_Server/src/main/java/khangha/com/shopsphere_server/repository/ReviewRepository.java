package khangha.com.shopsphere_server.repository;

import khangha.com.shopsphere_server.model.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByProductIdAndIsApprovedTrue(Long productId);
    
    List<Review> findByCustomerId(UUID customerId);
    
    List<Review> findByIsApprovedFalse();
    
    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.productId = :productId AND r.isApproved = true")
    Double getAverageRatingByProductId(@Param("productId") Long productId);
    
    @Query("SELECT COUNT(r) FROM Review r WHERE r.productId = :productId AND r.isApproved = true")
    Long getReviewCountByProductId(@Param("productId") Long productId);
}
