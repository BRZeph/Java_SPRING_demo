package brzeph.backend.spring.java_spring_demo.config;

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
import brzeph.backend.spring.java_spring_demo.entities.users.Client;
import brzeph.backend.spring.java_spring_demo.entities.users.User;
import brzeph.backend.spring.java_spring_demo.repositories.*;
import brzeph.backend.spring.java_spring_demo.services.SeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {
    /*
    Config class used for H2 DB.
     */

    @Autowired
    private SeedService seedService;

    @Override
    public void run(String... args) throws Exception {
        seedService.seedDatabase();
    }
}