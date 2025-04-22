package brzeph.spring.java_spring_demo.repositories;

import brzeph.spring.java_spring_demo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    /*
    A classe JpaRepository já tem implementação nativa para operações básicas.
     */
}
