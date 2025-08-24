package khangha.com.shopsphere_server.repository;

import khangha.com.shopsphere_server.model.entity.UserRole;
import khangha.com.shopsphere_server.model.entity.UserRoleId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleId> {
    List<UserRole> findByUserId(UUID userId);
}
