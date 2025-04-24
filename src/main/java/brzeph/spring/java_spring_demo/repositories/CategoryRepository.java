package brzeph.spring.java_spring_demo.repositories;

import brzeph.spring.java_spring_demo.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository  extends JpaRepository<Category, Long> {

}
