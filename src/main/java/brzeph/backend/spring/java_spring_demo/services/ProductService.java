package brzeph.backend.spring.java_spring_demo.services;

import brzeph.backend.spring.java_spring_demo.repositories.ProductRepository;
import brzeph.backend.spring.java_spring_demo.entities.products.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    public List<Product> findAll(){
        return repository.findAll();
    }

    public Product findById(Long id){
        Optional<Product> user = repository.findById(id);
        return user.get();
    }
}
