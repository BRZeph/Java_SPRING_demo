package brzeph.backend.spring.java_spring_demo.repositories;

import brzeph.backend.spring.java_spring_demo.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    /*
    A classe JpaRepository já tem implementação nativa para operações básicas.
     */
}
