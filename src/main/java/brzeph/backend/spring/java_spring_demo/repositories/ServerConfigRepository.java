package brzeph.backend.spring.java_spring_demo.repositories;

import brzeph.backend.spring.java_spring_demo.entities.serverConfig.ServerConfig;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ServerConfigRepository extends JpaRepository<ServerConfig, String> {
    Optional<ServerConfig> findByKey(String key);
}
