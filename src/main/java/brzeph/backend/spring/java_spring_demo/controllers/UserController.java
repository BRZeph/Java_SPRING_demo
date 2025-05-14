package brzeph.backend.spring.java_spring_demo.controllers;

import brzeph.backend.spring.java_spring_demo.dto.user.UserCreateDTO;
import brzeph.backend.spring.java_spring_demo.dto.user.UserReadDTO;
import brzeph.backend.spring.java_spring_demo.dto.user.UserUpdateDTO;
import brzeph.backend.spring.java_spring_demo.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/api/users")
public class UserController {

    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }


    @PreAuthorize("hasAuthority('READ_USER')")
    @GetMapping
    public ResponseEntity<List<UserReadDTO>> findAll() {
        List<UserReadDTO> users = service.findAll();
        return ResponseEntity.ok().body(users);
    }

    @PreAuthorize("hasAuthority('READ_USER')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<UserReadDTO> findById(@PathVariable Long id) {
        UserReadDTO dto = service.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @PreAuthorize("hasAuthority('CREATE_USER')")
    @PostMapping
//    public ResponseEntity<UserReadDTO> insert(@RequestBody @Validated UserCreateDTO dto) {
    public ResponseEntity<UserReadDTO> insert(@RequestBody @Valid UserCreateDTO dto) {
        UserReadDTO savedUser = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();
        return ResponseEntity.created(uri).body(savedUser);
    }

    @PreAuthorize("hasAuthority('DELETE_USER')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAuthority('UPDATE_USER')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<UserReadDTO> update(@PathVariable Long id, @RequestBody @Validated UserUpdateDTO dto) {
        UserReadDTO updatedUser = service.update(id, dto);
        return ResponseEntity.ok().body(updatedUser);
    }
}