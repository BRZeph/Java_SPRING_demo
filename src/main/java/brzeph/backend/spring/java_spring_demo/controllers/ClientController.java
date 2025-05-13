package brzeph.backend.spring.java_spring_demo.controllers;

import brzeph.backend.spring.java_spring_demo.dto.client.ClientCreateDTO;
import brzeph.backend.spring.java_spring_demo.dto.client.ClientReadDTO;
import brzeph.backend.spring.java_spring_demo.dto.client.ClientUpdateDTO;
import brzeph.backend.spring.java_spring_demo.dto.user.UserCreateDTO;
import brzeph.backend.spring.java_spring_demo.dto.user.UserReadDTO;
import brzeph.backend.spring.java_spring_demo.dto.user.UserUpdateDTO;
import brzeph.backend.spring.java_spring_demo.services.ClientService;
import brzeph.backend.spring.java_spring_demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/api/clients")
public class ClientController {

    private final ClientService service;

    @Autowired
    public ClientController(ClientService service) {
        this.service = service;
    }


    @PreAuthorize("hasAuthority('READ_CLIENT')")
    @GetMapping
    public ResponseEntity<List<ClientReadDTO>> findAll() {
        List<ClientReadDTO> users = service.findAll();
        return ResponseEntity.ok().body(users);
    }

    @PreAuthorize("hasAuthority('READ_CLIENT')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<ClientReadDTO> findById(@PathVariable Long id) {
        ClientReadDTO dto = service.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @PreAuthorize("hasAuthority('CREATE_CLIENT')")
    @PostMapping
    public ResponseEntity<ClientReadDTO> insert(@RequestBody @Validated ClientCreateDTO dto) {
        ClientReadDTO savedUser = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();
        return ResponseEntity.created(uri).body(savedUser);
    }

    @PreAuthorize("hasAuthority('DELETE_CLIENT')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAuthority('UPDATE_CLIENT')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<ClientReadDTO> update(@PathVariable Long id, @RequestBody @Validated ClientUpdateDTO dto) {
        ClientReadDTO updatedUser = service.update(id, dto);
        return ResponseEntity.ok().body(updatedUser);
    }
}