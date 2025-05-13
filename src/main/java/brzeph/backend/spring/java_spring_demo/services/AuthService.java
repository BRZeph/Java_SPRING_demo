package brzeph.backend.spring.java_spring_demo.services;

import brzeph.backend.spring.java_spring_demo.controllers.AuthController;
import brzeph.backend.spring.java_spring_demo.entities.users.User;
import brzeph.backend.spring.java_spring_demo.repositories.UserRepository;
import brzeph.backend.spring.java_spring_demo.entities.users.details.CustomUserDetails;
import brzeph.backend.spring.java_spring_demo.security.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class.getName());

    @Autowired
    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil,
                       UserService userService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    public AuthController.AuthResponse login(AuthController.AuthRequest request) {
        User user = userRepository.findByName(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        UserDetails userDetails = new CustomUserDetails(user);
        String jwt = jwtUtil.generateToken(userDetails);
        return new AuthController.AuthResponse(jwt);
    }

    public String register(AuthController.RegisterRequest request) {
        if (userRepository.findByName(request.getUsername()).isPresent()) {
            logger.info("Username is already in use: {}", request.getUsername());
            throw new IllegalArgumentException("Username already taken");
        }

        User user = new User();
        user.setName(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userService.insert(userService.getUserMapper().toCreateDTO(user));
        return "User registered successfully";
    }

    public Optional<User> getCurrentUser(CustomUserDetails userDetails) {
        if (userDetails == null) return Optional.empty();
        return userRepository.findByName(userDetails.getUsername());
    }
}

