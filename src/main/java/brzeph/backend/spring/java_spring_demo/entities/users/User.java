package brzeph.backend.spring.java_spring_demo.entities.users;

import brzeph.backend.spring.java_spring_demo.entities.orders.Order;
import brzeph.backend.spring.java_spring_demo.entities.permissions.Role;
import brzeph.backend.spring.java_spring_demo.entities.permissions.enums.RoleSeed;
import jakarta.persistence.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serial;
import java.io.Serializable;
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
    private Role role;
    private String email;
    private String password;
    private String phone;

    @OneToMany(mappedBy = "client")
    private List<Order> orders = new ArrayList<>();

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public User(){
    }

    public User(Long id, String name, String email, String password, String phone, Role role) {
        this.id = id;
        this.name = name;
        if (role != null) {
            this.role = role;
            logger.debug("role={}", role);
        } else {
            logger.debug("role is null");
            this.role = RoleSeed.EMPTY_ROLE.toRole();
        }
        this.email = email;
        this.password = password;
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", role=" + role +
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
}
