package brzeph.backend.spring.java_spring_demo.dto.user;

import brzeph.backend.spring.java_spring_demo.dto.role.RoleDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.Objects;

public class UserCreateDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    @NotBlank(message = "Name cannot be blank.")
    private String name;

    @Valid
    @NotNull(message = "Role must be specified.")
    private RoleDTO roleUser;

    @Email
    @NotBlank(message = "Email cannot be blank.")
    private String email;

    @NotBlank(message = "Password cannot be blank.")
    @Size(min = 6, message = "Password must be over 6 digits.")
    private String password;

    private String phone;

    public UserCreateDTO() {
    }

    public UserCreateDTO(Long id, String name, RoleDTO roleUser, String email, String password, String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.roleUser = roleUser;
    }

    @Override
    public String toString() {
        return "UserCreateDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", roleUser='" + roleUser + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserCreateDTO that = (UserCreateDTO) o;
        return Objects.equals(name, that.name) && Objects.equals(email, that.email) && Objects.equals(password, that.password) && Objects.equals(phone, that.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email, password, phone);
    }

    public @NotBlank String getName() {
        return name;
    }

    public void setName(@NotBlank String name) {
        this.name = name;
    }

    public @Email @NotBlank String getEmail() {
        return email;
    }

    public void setEmail(@Email @NotBlank String email) {
        this.email = email;
    }

    public @NotBlank @Size(min = 6) String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank @Size(min = 6) String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getId() {
        return id;
    }

    public @NotNull(message = "Role must be specified.") RoleDTO getRoleUser() {
        return roleUser;
    }

    public void setRoleUser(@NotNull(message = "Role must be specified.") RoleDTO roleUser) {
        this.roleUser = roleUser;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

