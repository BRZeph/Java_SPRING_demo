package brzeph.backend.spring.java_spring_demo.services;

import brzeph.backend.spring.java_spring_demo.entities.serverConfig.ServerConfig;
import brzeph.backend.spring.java_spring_demo.repositories.ServerConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static brzeph.backend.spring.java_spring_demo.entities.serverConfig.ServerConfigEnum.POSITIVE;
import static brzeph.backend.spring.java_spring_demo.entities.serverConfig.ServerConfigEnum.SEEDED_DB;

@Service
public class ServerConfigService {

    private final ServerConfigRepository serverConfigRepository;

    @Autowired
    public ServerConfigService(ServerConfigRepository serverConfigRepository) {
        this.serverConfigRepository = serverConfigRepository;
    }

    public Optional<ServerConfig> getConfigByKey(String key) {
        return serverConfigRepository.findByKey(key);
    }

    public List<ServerConfig> getAllConfigs() {
        return serverConfigRepository.findAll();
    }

    public String getConfigValue(String key){
        return serverConfigRepository.findByKey(key)
                .map(ServerConfig::getValue)
                .orElse(null);
    }

    public boolean isDatabaseSeeded(){
        String value = getConfigValue(SEEDED_DB.getKey());
        return POSITIVE.getKey().equals(value);
    }
}
