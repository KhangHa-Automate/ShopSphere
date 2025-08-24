package khangha.com.shopsphere_server.repository;

import khangha.com.shopsphere_server.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findBySlug(String slug);
    
    List<Category> findByParentIdIsNull();
    
    List<Category> findByParentId(Long parentId);
    
    @Query("SELECT c FROM Category c WHERE c.lft >= :lft AND c.rgt <= :rgt ORDER BY c.lft")
    List<Category> findDescendants(@org.springframework.data.repository.query.Param("lft") Integer lft, 
                                   @org.springframework.data.repository.query.Param("rgt") Integer rgt);
    
    boolean existsBySlug(String slug);
}
