package brzeph.backend.spring.java_spring_demo.config;

import brzeph.backend.spring.java_spring_demo.entities.orders.Order;
import brzeph.backend.spring.java_spring_demo.entities.orders.OrderItem;
import brzeph.backend.spring.java_spring_demo.entities.orders.Payment;
import brzeph.backend.spring.java_spring_demo.entities.orders.enums.OrderStatus;
import brzeph.backend.spring.java_spring_demo.entities.products.ProductCategory;
import brzeph.backend.spring.java_spring_demo.entities.products.Product;
import brzeph.backend.spring.java_spring_demo.entities.users.User;
import brzeph.backend.spring.java_spring_demo.repositories.*;
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

        User user1 = new User(null, "Alice Souza", "alice.souza@example.com", "dadoSeguroNãoVazar", "5511987654321");
        User user2 = new User(null, "Bruno Lima", "bruno.lima@example.com", "senha123", "5511976543210");
        User user3 = new User(null, "Carla Mendes", "carla.mendes@example.com", "senha123", "5511965432109");
        userRepository.saveAll(Arrays.asList(user1, user2, user3));

        ProductCategory cat1 = new ProductCategory(null, "Livros");
        ProductCategory cat2 = new ProductCategory(null, "Eletrônicos");
        ProductCategory cat3 = new ProductCategory(null, "Casa & Cozinha");
        categoryRepository.saveAll(Arrays.asList(cat1, cat2, cat3));

        Product p1 = new Product(null, "Notebook Dell XPS 13", "Notebook ultrafino de alta performance", 8999.90, "https://example.com/xps13");
        Product p2 = new Product(null, "Fritadeira AirFryer", "Fritadeira sem óleo com tecnologia avançada", 599.90, "https://example.com/airfryer");
        Product p3 = new Product(null, "Livro: Clean Code", "Livro sobre boas práticas de programação", 120.00, "https://example.com/cleancode");
        Product p4 = new Product(null, "Smartphone Galaxy S22", "Última geração do smartphone da Samsung", 4999.90, "https://example.com/galaxys22");
        Product p5 = new Product(null, "Livro: Design Patterns", "Catálogo de padrões de projeto em software", 150.00, "https://example.com/designpatterns");

        p1.getCategories().add(cat2);
        p2.getCategories().add(cat3);
        p3.getCategories().add(cat1);
        p4.getCategories().add(cat2);
        p5.getCategories().add(cat1);

        productRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5));

        Order order1 = new Order(null, Instant.parse("2025-04-20T10:15:30Z"), OrderStatus.PAID, user1);
        Order order2 = new Order(null, Instant.parse("2025-05-15T14:20:00Z"), OrderStatus.WAITING_PAYMENT, user2);
        Order order3 = new Order(null, Instant.parse("2025-06-10T08:45:15Z"), OrderStatus.SHIPPED, user1);
        Order order4 = new Order(null, Instant.parse("2025-07-05T17:30:45Z"), OrderStatus.DELIVERED, user3);
        Order order5 = new Order(null, Instant.parse("2025-08-01T12:00:00Z"), OrderStatus.CANCELLED, user2);

        orderRepository.saveAll(Arrays.asList(order1, order2, order3, order4, order5));

        OrderItem oi1 = new OrderItem(order1, p3, 1, p3.getPrice()); // Clean Code
        OrderItem oi2 = new OrderItem(order1, p1, 1, p1.getPrice()); // Notebook
        OrderItem oi3 = new OrderItem(order2, p2, 2, p2.getPrice()); // AirFryer
        OrderItem oi4 = new OrderItem(order3, p4, 1, p4.getPrice()); // Galaxy S22
        OrderItem oi5 = new OrderItem(order4, p5, 3, p5.getPrice()); // Design Patterns
        orderItemRepository.saveAll(Arrays.asList(oi1, oi2, oi3, oi4, oi5));

        Payment payment1 = new Payment(null, Instant.parse("2025-04-20T12:00:00Z"), order1);
        order1.setPayment(payment1);
        orderRepository.save(order1);
    }
}