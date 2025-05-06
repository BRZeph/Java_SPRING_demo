package brzeph.backend.spring.java_spring_demo.services;

import brzeph.backend.spring.java_spring_demo.dto.user.UserCreateDTO;
import brzeph.backend.spring.java_spring_demo.dto.user.UserReadDTO;
import brzeph.backend.spring.java_spring_demo.dto.user.UserUpdateDTO;
import brzeph.backend.spring.java_spring_demo.entities.users.User;
import brzeph.backend.spring.java_spring_demo.mappers.UserMapper;
import brzeph.backend.spring.java_spring_demo.repositories.UserRepository;
import brzeph.backend.spring.java_spring_demo.security.CustomUserDetails;
import brzeph.backend.spring.java_spring_demo.services.exceptions.DatabaseException;
import brzeph.backend.spring.java_spring_demo.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class UserService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class.getName());

    private final UserRepository repository;
    private final UserMapper userMapper;
    private final AuditLogService auditLogService;

    @Autowired
    public UserService(UserRepository repository, UserMapper userMapper, AuditLogService auditLogService) {
        this.repository = repository;
        this.userMapper = userMapper;
        this.auditLogService = auditLogService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                new ArrayList<>()
        );
    }

    public List<UserReadDTO> findAll() {
        logger.info("Find all users");
        List<User> users = repository.findAll();
        return users.stream()
                .map(userMapper::toReadDTO)  // Using UserReadDTO to exclude sensitive data
                .collect(Collectors.toList());
    }

    public UserReadDTO findById(Long id) {
        logger.info("Find User by id: {}", id);
        User user = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
        return userMapper.toReadDTO(user);
    }

    public UserReadDTO insert(UserCreateDTO dto) {
        logger.info("Insert User: {}", dto);
        User entity = userMapper.fromCreateDTO(dto);
        User saved = repository.save(entity);

        // Extract acting user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails customUserDetails) {
            User actingUser = customUserDetails.getUser();

            auditLogService.log(
                    actingUser,
                    "CREATE_USER",
                    "User with id " + saved.getId() + " was created"
            );
        } else {
            logger.warn("No authenticated user found when trying to log action, using dummy instead.");
            auditLogService.log(
                    null,
                    "CREATE_USER",
                    "User with id " + saved.getId() + " was created"
            );
        }

        return userMapper.toReadDTO(saved);
    }

    public void delete(Long id) {
        logger.info("Delete User by id: {}", id);
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException(id);
        }
        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public UserReadDTO update(Long id, UserUpdateDTO dto) {
        logger.info("Update User by id: {}", id);
        try {
            User entity = repository.getReferenceById(id);
            updateData(entity, dto);
            User updated = repository.save(entity);
            return userMapper.toReadDTO(updated);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    private void updateData(User entity, UserUpdateDTO dto) {
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

    public UserMapper getUserMapper() {
        return userMapper;
    }
}