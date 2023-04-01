package tv.wouri.speak.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tv.wouri.speak.models.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
