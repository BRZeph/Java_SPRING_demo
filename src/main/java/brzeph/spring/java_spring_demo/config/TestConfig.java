package brzeph.spring.java_spring_demo.config;

import brzeph.spring.java_spring_demo.entities.Order;
import brzeph.spring.java_spring_demo.entities.User;
import brzeph.spring.java_spring_demo.entities.enums.OrderStatus;
import brzeph.spring.java_spring_demo.repositories.OrderRepository;
import brzeph.spring.java_spring_demo.repositories.UserRepository;
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
    }
}
