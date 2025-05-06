package brzeph.backend.spring.java_spring_demo.security;

import brzeph.backend.spring.java_spring_demo.entities.users.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class CustomUserDetails implements UserDetails {

    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    // Example: returns a single role for simplicity. Extend if needed.
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(
                new SimpleGrantedAuthority("ROLE_USER") // Replace or enhance with actual roles if needed
        );
    }

    @Override
    public String getPassword() {
        return user.getPassword();  // assuming your User entity stores hashed password
    }

    @Override
    public String getUsername() {
        return user.getEmail(); // or user.getUsername() if you have such a field
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // customize this if your domain handles expiration
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // customize this if you track account locks
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // customize this if credentials can expire
    }

    @Override
    public boolean isEnabled() {
        return true; // you can add an `enabled` field to your User entity if needed
    }

    public User getUser() {
        return user; // exposes the wrapped user entity if needed elsewhere
    }
}
