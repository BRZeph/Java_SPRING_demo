package brzeph.backend.spring.java_spring_demo.services;

import brzeph.backend.spring.java_spring_demo.dto.user.UserCreateDTO;
import brzeph.backend.spring.java_spring_demo.dto.user.UserReadDTO;
import brzeph.backend.spring.java_spring_demo.dto.user.UserUpdateDTO;
import brzeph.backend.spring.java_spring_demo.entities.permissions.enums.RoleSeed;
import brzeph.backend.spring.java_spring_demo.entities.users.User;
import brzeph.backend.spring.java_spring_demo.mappers.UserMapper;
import brzeph.backend.spring.java_spring_demo.repositories.RoleRepository;
import brzeph.backend.spring.java_spring_demo.repositories.UserRepository;
import brzeph.backend.spring.java_spring_demo.entities.users.details.CustomUserDetails;
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

    private final RoleRepository roleRepository;
    private final UserRepository repository;
    private final UserMapper userMapper;
    private final AuditLogService auditLogService;

    @Autowired
    public UserService(RoleRepository roleRepository, UserRepository repository, UserMapper userMapper, AuditLogService auditLogService) {
        this.roleRepository = roleRepository;
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
        for (User user : users) {
            user.setRoleUser(roleRepository.findById(user.getRoleUser().getId()).orElseThrow(RuntimeException::new));
        }
        return users.stream()
                .map(userMapper::toReadDTO)
                .collect(Collectors.toList());
    }

    public UserReadDTO findById(Long id) {
        logger.info("Find User by id: {}", id);
        User user = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
        return userMapper.toReadDTO(user);
    }

    public UserReadDTO insert(UserCreateDTO dto) {
        User entity = userMapper.fromCreateDTO(dto);
        entity.setRoleUser(RoleSeed.getRoleByName(dto.getRoleUser().getName()).toRole());
        User saved = repository.save(entity);

        logger.info("User saved: {}", saved.getId());
        validateAndLog(saved.getId(), "CREATE_USER", " was created");

        return userMapper.toReadDTO(saved);
    }

    public void delete(Long id) {
        logger.info("Delete User by id: {}", id);
        validateAndLog(id, "DELETE_USER", " was deleted");
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
        validateAndLog(id, "UPDATE_USER", " was updated");
        try {
            User entity = repository.getReferenceById(id);
            entity.setRoleUser(RoleSeed.getRoleByName(dto.getRoleUser().getName()).toRole());
            User updated = repository.save(entity);
            return userMapper.toReadDTO(updated);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        } catch (RuntimeException e){
            throw new ResourceNotFoundException(dto.getRoleUser().getName());
        }
    }

    private void validateAndLog(Long id, String action, String suffix) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object principal = (auth != null) ? auth.getPrincipal() : null;

        if (principal instanceof CustomUserDetails customUserDetails) {
            auditLogService.log(customUserDetails.getUser(), action, "User with id " + id + suffix);
        } else {
            auditLogService.log(principal.toString(), action, "User with id " + id + suffix);
        }
    }


    public UserMapper getUserMapper() {
        return userMapper;
    }
}