package brzeph.backend.spring.java_spring_demo.controllers;

import brzeph.backend.spring.java_spring_demo.entities.permissions.Role;
import brzeph.backend.spring.java_spring_demo.entities.users.details.CustomUserDetails;
import brzeph.backend.spring.java_spring_demo.services.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class.getName());

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        logger.info("Login attempt for username: {}", request.getUsername());
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        logger.info("Register attempt for username: {}", request.getUsername());
        try {
            String message = authService.register(request);
            return ResponseEntity.ok(message);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PreAuthorize("hasAuthority('READ_SELF')")
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(@AuthenticationPrincipal CustomUserDetails userDetails) {
        if (userDetails == null){
            logger.debug("User not found");
            throw new RuntimeException("NULL USER. Replace this later");
        }
        logger.info("Current user: {}", userDetails.getUser().getId());
        return authService.getCurrentUser(userDetails)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(401).body("Unauthorized"));
    }

    public static class AuthRequest {
        private String username;
        private String password;

        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }

        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }

    public static class RegisterRequest {
        private String username;
        private Role roleUser;
        private String email;
        private String password;
        private String phone;

        public Role getRoleUser() {
            return roleUser;
        }

        public void setRoleUser(Role roleUser) {
            this.roleUser = roleUser;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        @Override
        public String toString() {
            return "RegisterRequest{" +
                    "username='" + username + '\'' +
                    ", roleUser=" + roleUser.getName() +
                    ", email='" + email + '\'' +
                    ", password='" + password + '\'' +
                    ", phone='" + phone + '\'' +
                    '}';
        }
    }

    public record AuthResponse(String jwt) {
    }
}
