package brzeph.backend.spring.java_spring_demo.entities.users;

import brzeph.backend.spring.java_spring_demo.entities.orders.Order;
import brzeph.backend.spring.java_spring_demo.entities.permissions.Role;
import brzeph.backend.spring.java_spring_demo.entities.permissions.enums.RoleSeed;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tb_users")
public class User implements Serializable {
    /*
    tb_users → Middle client (will manage the app)
    tb_clients → Final client (will access the app to buy/etc)
     */

    private static final Logger logger = LoggerFactory.getLogger(User.class.getName());

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role roleUser;
    private String email;
    private String password;
    private String phone;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private Instant createdAt;

    @OneToMany(mappedBy = "client")
    private List<Order> orders = new ArrayList<>();

    public User(){
        this.roleUser = RoleSeed.EMPTY_ROLE.toRole();
    }

    public User(Long id, String name, Role roleUser, String email, String password, String phone) {
        this.id = id;
        this.name = name;
        if (roleUser != null) {
            this.roleUser = roleUser;
        } else {
            this.roleUser = RoleSeed.EMPTY_ROLE.toRole();
        }
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.createdAt = Instant.now();
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", role=" + roleUser +
                ", email='" + email + '\'' +
                ", password='" + "[PROTECTED]" + '\'' +
                ", phone='" + phone + '\'' +
                ", orders=" + "[to see orders, user /api/orders/{id} end-point]" +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(name, user.name) && Objects.equals(email, user.email) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, password);
    }

    public Role getRoleUser() {
        return roleUser;
    }

    public void setRoleUser(Role roleUser) {
        this.roleUser = roleUser;
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

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
