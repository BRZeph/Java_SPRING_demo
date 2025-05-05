package brzeph.backend.spring.java_spring_demo.services;

import brzeph.backend.spring.java_spring_demo.entities.products.ProductCategory;
import brzeph.backend.spring.java_spring_demo.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    public List<ProductCategory> findAll(){
        return repository.findAll();
    }

    public ProductCategory findById(Long id){
        Optional<ProductCategory> user = repository.findById(id);
        return user.get();
    }
}
