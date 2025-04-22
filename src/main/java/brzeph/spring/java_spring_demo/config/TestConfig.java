package brzeph.spring.java_spring_demo.config;

import brzeph.spring.java_spring_demo.entities.User;
import brzeph.spring.java_spring_demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;

@Configuration
@Profile("test")
/*
TEM que ser o mesmo do application.properties.
Vai funcionar apenas para o perfil teste.
 */
public class TestConfig implements CommandLineRunner {
    /*
    Fazer DB Seeding (popular DB).
     */

    @Autowired //Fazer injeção de dados do BD implícita pelo framework.
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {

        User user1 = new User(null, "Joao", "Joao@gmail.com", "123456", "551612345678");
        User user2 = new User(null, "Maria", "Maria@gmail.com", "123456", "551612345678");

        userRepository.saveAll(Arrays.asList(user1, user2));
    }
}
