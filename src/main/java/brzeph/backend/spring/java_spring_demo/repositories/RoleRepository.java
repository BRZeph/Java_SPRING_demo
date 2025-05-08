package brzeph.backend.spring.java_spring_demo.repositories;

import brzeph.backend.spring.java_spring_demo.entities.permissions.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
