package brzeph.spring.java_spring_demo.config;

import brzeph.spring.java_spring_demo.entities.*;
import brzeph.spring.java_spring_demo.entities.enums.OrderStatus;
import brzeph.spring.java_spring_demo.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.Instant;
import java.util.Arrays;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    public void run(String... args) throws Exception {

        User user1 = new User(null, "Joao", "Joao@gmail.com", "123456", "551612345678");
        User user2 = new User(null, "Maria", "Maria@gmail.com", "123456", "551612345678");
        userRepository.saveAll(Arrays.asList(user1, user2));

        Order order1 = new Order(null, Instant.parse("2025-04-22T20:53:07Z"), OrderStatus.CANCELLED, user1);
        Order order2 = new Order(null, Instant.parse("2025-05-22T21:53:07Z"), OrderStatus.PAID, user1);
        Order order3 = new Order(null, Instant.parse("2025-06-22T22:53:07Z"), OrderStatus.DELIVERED, user2);
        Order order4 = new Order(null, Instant.parse("2025-07-22T23:53:07Z"), OrderStatus.SHIPPED, user2);
        orderRepository.saveAll(Arrays.asList(order1, order2, order3, order4));

        Category cat1 = new Category(null, "Book");
        Category cat2 = new Category(null, "Tech");
        categoryRepository.saveAll(Arrays.asList(cat1, cat2));

        Product p1 = new Product(null, "prod1", "descr1", 1000d, "www.google.com");
        Product p2 = new Product(null, "prod2", "descr2", 2000d, "www.google.com");
        Product p3 = new Product(null, "prod3", "descr3", 3000d, "www.google.com");
        p1.getCategories().add(cat1);
        p2.getCategories().add(cat2);
        p3.getCategories().add(cat1);
        productRepository.saveAll(Arrays.asList(p1, p2, p3));

        OrderItem oi1 = new OrderItem(order1, p1, 2, 0d);
        OrderItem oi2 = new OrderItem(order1, p3, 3, p3.getPrice());
        OrderItem oi3 = new OrderItem(order2, p2, 4, p2.getPrice());
        OrderItem oi4 = new OrderItem(order3, p3, 5, p3.getPrice());
        orderItemRepository.saveAll(Arrays.asList(oi1, oi2, oi3, oi4));
    }
}
