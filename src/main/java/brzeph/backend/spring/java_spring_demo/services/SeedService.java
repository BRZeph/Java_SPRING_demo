package brzeph.backend.spring.java_spring_demo.services;

import brzeph.backend.spring.java_spring_demo.entities.orders.Order;
import brzeph.backend.spring.java_spring_demo.entities.orders.OrderItem;
import brzeph.backend.spring.java_spring_demo.entities.orders.Payment;
import brzeph.backend.spring.java_spring_demo.entities.orders.enums.OrderStatus;
import brzeph.backend.spring.java_spring_demo.entities.permissions.Permission;
import brzeph.backend.spring.java_spring_demo.entities.permissions.Role;
import brzeph.backend.spring.java_spring_demo.entities.permissions.RolePermission;
import brzeph.backend.spring.java_spring_demo.entities.permissions.enums.PermissionSeed;
import brzeph.backend.spring.java_spring_demo.entities.permissions.enums.RoleSeed;
import brzeph.backend.spring.java_spring_demo.entities.products.Product;
import brzeph.backend.spring.java_spring_demo.entities.products.ProductCategory;
import brzeph.backend.spring.java_spring_demo.entities.serverConfig.ServerConfig;
import brzeph.backend.spring.java_spring_demo.entities.users.Client;
import brzeph.backend.spring.java_spring_demo.entities.users.User;
import brzeph.backend.spring.java_spring_demo.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static brzeph.backend.spring.java_spring_demo.entities.serverConfig.ServerConfigEnum.*;

@Service
public class SeedService {
    /*
    Used for seeding the DB with data.
     */
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RolePermissionRepository rolePermissionRepository;

    @Autowired
    private ServerConfigRepository serverConfigRepository;

    public void seedDatabase(){

        List<ServerConfig> serverConfigs = List.of(
                new ServerConfig(SEEDED_DB.getKey(), POSITIVE.getKey(), SEEDED_DB.getDescription(), Instant.now()),
                new ServerConfig(DEV_DB.getKey(), POSITIVE.getKey(), DEV_DB.getDescription(), Instant.now())
        );
        serverConfigRepository.saveAll(serverConfigs);

        List<Permission> permissions = new ArrayList<>();
        for (PermissionSeed perm : PermissionSeed.values()) {
            permissions.add(perm.toPermission());
            perm.setSystemDefined(true);
        }
        permissionRepository.saveAll(permissions);

        List<Role> roles = new ArrayList<>();
        for (RoleSeed role : RoleSeed.values()) {
            roles.add(role.toRole());
            role.setSystemDefined(true);
        }
        roleRepository.saveAll(roles);

        List<RolePermission> rolePermissions = new ArrayList<>();
        for (Role role : roles) {
            for (Permission perm : role.getPermissions()){
                rolePermissions.add(new RolePermission(null, role, perm));
            }
        }
        rolePermissionRepository.saveAll(rolePermissions);

        User user1 = new User(null, "Alice Souza", null, "alice.souza@example.com", "dadoSeguroNãoVazar", "5511987654321");
        User user2 = new User(null, "Bruno Lima", null, "bruno.lima@example.com", "senha123", "5511976543210");
        User user3 = new User(null, "Carla Mendes", null, "carla.mendes@example.com", "senha123", "5511965432109");
        user1.setRoleUser(RoleSeed.ADMIN.toRole());
        user2.setRoleUser(RoleSeed.VENDOR.toRole());
        user3.setRoleUser(RoleSeed.EMPTY_ROLE.toRole());
        userRepository.saveAll(Arrays.asList(user1, user2, user3));

        Client client1 = new Client(null, "Client1", "alice.souza@example.com", "dadoSeguroNãoVazar", "5511987654321");
        Client client2 = new Client(null, "Client2", "bruno.lima@example.com", "senha123", "5511976543210");
        Client client3 = new Client(null, "Client3", "carla.mendes@example.com", "senha123", "5511965432109");
        clientRepository.saveAll(Arrays.asList(client1, client2, client3));

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
