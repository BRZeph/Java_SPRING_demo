package brzeph.backend.spring.java_spring_demo.services;

import brzeph.backend.spring.java_spring_demo.entities.User;
import brzeph.backend.spring.java_spring_demo.repositories.UserRepository;
import brzeph.backend.spring.java_spring_demo.services.exceptions.DatabaseException;
import brzeph.backend.spring.java_spring_demo.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public List<User> findAll(){
        return repository.findAll();
    }

    public User findById(Long id){
        Optional<User> user = repository.findById(id);
        return user.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public User insert(User obj){
        return repository.save(obj);
    }

    public void delete(Long id){
        if (!repository.existsById(id)){
            throw new ResourceNotFoundException(id);
        }
        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e){ // Trying to delete but user has constraints (ex: orders).
            throw new DatabaseException(e.getMessage());
        }
    }

    public User update(Long id, User obj){
        try {
            User entity = repository.getReferenceById(id);
            updateData(entity, obj);
            return repository.save(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    private void updateData(User entity, User obj) {
        entity.setName(obj.getName());
        entity.setEmail(obj.getEmail());
        entity.setPhone(obj.getPhone());
    }
}
