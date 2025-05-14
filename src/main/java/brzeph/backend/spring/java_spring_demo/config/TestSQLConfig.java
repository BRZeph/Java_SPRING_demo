package brzeph.backend.spring.java_spring_demo.config;

import brzeph.backend.spring.java_spring_demo.entities.permissions.Permission;
import brzeph.backend.spring.java_spring_demo.entities.permissions.Role;
import brzeph.backend.spring.java_spring_demo.entities.permissions.enums.PermissionSeed;
import brzeph.backend.spring.java_spring_demo.entities.permissions.enums.RoleSeed;
import brzeph.backend.spring.java_spring_demo.entities.serverConfig.ServerConfig;
import brzeph.backend.spring.java_spring_demo.repositories.PermissionRepository;
import brzeph.backend.spring.java_spring_demo.repositories.RoleRepository;
import brzeph.backend.spring.java_spring_demo.repositories.ServerConfigRepository;
import brzeph.backend.spring.java_spring_demo.services.SeedService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;
import java.util.Optional;

import static brzeph.backend.spring.java_spring_demo.entities.serverConfig.ServerConfigEnum.POSITIVE;
import static brzeph.backend.spring.java_spring_demo.entities.serverConfig.ServerConfigEnum.SEEDED_DB;

@Configuration
@Profile("testSQL")
public class TestSQLConfig implements CommandLineRunner {
    /*
    Config class used for mySQL workbench DB.
     */

    private static final Logger logger = LoggerFactory.getLogger(TestSQLConfig.class.getName());

    @Autowired
    private SeedService seedService;

    @Autowired
    private ServerConfigRepository serverConfigRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {

        persistData(permissionRepository, roleRepository);

        if (isDbIsSeeded(serverConfigRepository)) {
            return;
        }

        seedService.seedDatabase();
    }

    private static void persistData(PermissionRepository permissionRepository, RoleRepository roleRepository) {
        List<Permission> permissions = permissionRepository.findAll();
        List<Role> role = roleRepository.findAll();
        PermissionSeed.persist(permissions);
        RoleSeed.persist(role);
    }

    private static boolean isDbIsSeeded(ServerConfigRepository serverConfigRepository) {
        Optional<ServerConfig> serverConfigOptional = serverConfigRepository.findByKey(SEEDED_DB.getKey());

        boolean dbIsSeeded = true;

        if (serverConfigOptional.isPresent()) { // Key SEEDED_DB exists in DB, check for value.
            ServerConfig serverConfig = serverConfigOptional.get();

            if (!POSITIVE.getKey().equals(serverConfig.getValue())) { // Key SEEDED_DB exist in DB but has NEGATIVE value, seed DB.
                dbIsSeeded = false;
            }

        } else { // key SEEDED_DB does not exist in DB, seed DB.
            dbIsSeeded = false;
        }

        logger.info(dbIsSeeded ? "Database seeded" : "Database not seeded");

        return dbIsSeeded;
    }
}