package brzeph.backend.spring.java_spring_demo.services;

import brzeph.backend.spring.java_spring_demo.dto.client.ClientCreateDTO;
import brzeph.backend.spring.java_spring_demo.dto.client.ClientReadDTO;
import brzeph.backend.spring.java_spring_demo.dto.client.ClientUpdateDTO;
import brzeph.backend.spring.java_spring_demo.entities.users.Client;
import brzeph.backend.spring.java_spring_demo.entities.users.details.CustomClientDetails;
import brzeph.backend.spring.java_spring_demo.mappers.ClientMapper;
import brzeph.backend.spring.java_spring_demo.repositories.ClientRepository;
import brzeph.backend.spring.java_spring_demo.services.exceptions.DatabaseException;
import brzeph.backend.spring.java_spring_demo.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(ClientService.class.getName());

    private final ClientRepository repository;
    private final ClientMapper mapper;
    private final AuditLogService auditLogService;

    @Autowired
    public ClientService(ClientRepository repository, @Qualifier("clientMapperImpl") ClientMapper mapper, AuditLogService auditLogService) {
        this.repository = repository;
        this.mapper = mapper;
        this.auditLogService = auditLogService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Client user = repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new User(
                user.getEmail(),
                user.getPassword(),
                new ArrayList<>()
        );
    }

    public List<ClientReadDTO> findAll() {
        logger.info("Find all clients");
        List<Client> clients = repository.findAll();
        return clients.stream()
                .map(mapper::toReadDTO)
                .collect(Collectors.toList());
    }

    public ClientReadDTO findById(Long id) {
        logger.info("Find client by id: {}", id);
        Client user = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
        return mapper.toReadDTO(user);
    }

    public ClientReadDTO insert(ClientCreateDTO dto) {
        logger.info("Insert client: {}", dto);
        Client entity = mapper.fromCreateDTO(dto);
        Client saved = repository.save(entity);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CustomClientDetails customClientDetails) {
            Client actingUser = customClientDetails.getClient();

            auditLogService.log(
                    actingUser,
                    "CREATE_CLIENT",
                    "Client with id " + saved.getId() + " was created"
            );
        } else {
            logger.warn("No authenticated Client found when trying to log action, using dummy instead.");
            auditLogService.log(
                    (Client) null,
                    "CREATE_CLIENT",
                    "Client with id " + saved.getId() + " was created"
            );
        }

        return mapper.toReadDTO(saved);
    }

    public void delete(Long id) {
        logger.info("Delete Client by id: {}", id);
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException(id);
        }
        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public ClientReadDTO update(Long id, ClientUpdateDTO dto) {
        logger.info("Update Client by id: {}", id);
        try {
            Client entity = repository.getReferenceById(id);
            updateData(entity, dto);
            Client updated = repository.save(entity);
            return mapper.toReadDTO(updated);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    private void updateData(Client entity, ClientUpdateDTO dto) {
        logger.debug("entity: {}", entity);
        logger.debug("dto: {}", dto);
        if (dto.getName() != null) {
            entity.setName(dto.getName());
        }
        if (dto.getEmail() != null) {
            entity.setEmail(dto.getEmail());
        }
        if (dto.getPhone() != null) {
            entity.setPhone(dto.getPhone());
        }
        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            entity.setPassword(dto.getPassword());
        }
    }

    public ClientMapper getMapper() {
        return mapper;
    }
}