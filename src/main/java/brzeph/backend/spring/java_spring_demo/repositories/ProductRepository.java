package brzeph.backend.spring.java_spring_demo.repositories;

import brzeph.backend.spring.java_spring_demo.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
