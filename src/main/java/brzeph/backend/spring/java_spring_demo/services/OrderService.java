package brzeph.backend.spring.java_spring_demo.services;

import brzeph.backend.spring.java_spring_demo.entities.orders.Order;
import brzeph.backend.spring.java_spring_demo.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;

    public List<Order> findAll(){
        return repository.findAll();
    }

    public Order findById(Long id){
        Optional<Order> user = repository.findById(id);
        return user.get();
    }
}
