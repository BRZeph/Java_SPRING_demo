package brzeph.spring.java_spring_demo.repositories;

import brzeph.spring.java_spring_demo.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    /*
    A classe JpaRepository já tem implementação nativa para operações básicas.
     */
}
