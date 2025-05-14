package brzeph.backend.spring.java_spring_demo.dto.client;

import brzeph.backend.spring.java_spring_demo.entities.orders.Order;
import brzeph.backend.spring.java_spring_demo.entities.permissions.Role;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ClientUpdateDTO {

    private Long id;
    private String name;
    private Role role;
    private String email;
    private String password;
    private String phone;
    private List<Order> orders = new ArrayList<>();

    public ClientUpdateDTO() {
    }

    public ClientUpdateDTO(Long id, String name, Role role, String email, String password, String phone, List<Order> orders) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.orders = orders;
    }

    @Override
    public String toString() {
        return "ClientUpdateDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", role=" + role +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", orders=" + orders +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ClientUpdateDTO that = (ClientUpdateDTO) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
