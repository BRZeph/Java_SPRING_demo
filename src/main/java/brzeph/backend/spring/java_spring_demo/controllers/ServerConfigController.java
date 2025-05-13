package brzeph.backend.spring.java_spring_demo.controllers;

import brzeph.backend.spring.java_spring_demo.dto.serverConfig.ServerConfigDTO;
import brzeph.backend.spring.java_spring_demo.entities.serverConfig.ServerConfig;
import brzeph.backend.spring.java_spring_demo.services.ServerConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/serverConfig")
public class ServerConfigController {

    private final ServerConfigService serverConfigService;

    @Autowired
    public ServerConfigController(ServerConfigService serverConfigService) {
        this.serverConfigService = serverConfigService;
    }

    @PreAuthorize("hasAuthority('READ_SERVER_CONFIG')")
    @GetMapping("/is-seeded")
    public ResponseEntity<Map<String, Boolean>> checkIfDatabaseSeeded() {
        boolean isSeeded = serverConfigService.isDatabaseSeeded();
        Map<String, Boolean> response = Map.of("isSeeded", isSeeded);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('READ_SERVER_CONFIG')")
    @GetMapping("/config-value")
    public ResponseEntity<List<ServerConfigDTO>> getAllConfigs() {
        List<ServerConfigDTO> dtos = serverConfigService.getAllConfigs().stream()
                .map(config -> new ServerConfigDTO(
                        config.getKey(),
                        config.getValue(),
                        config.getDescription(),
                        config.getUpdatedAt()
                ))
                .toList();

        if (dtos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(dtos);
    }

    @PreAuthorize("hasAuthority('READ_SERVER_CONFIG')")
    @GetMapping("/config-value/{key}")
    public ResponseEntity<ServerConfigDTO> getConfigByKey(@PathVariable String key) {
        return serverConfigService.getConfigByKey(key)
                .map(config -> new ServerConfigDTO(
                        config.getKey(),
                        config.getValue(),
                        config.getDescription(),
                        config.getUpdatedAt()
                ))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
