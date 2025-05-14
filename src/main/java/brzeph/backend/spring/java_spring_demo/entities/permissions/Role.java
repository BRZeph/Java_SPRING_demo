package brzeph.backend.spring.java_spring_demo.entities.permissions;

import brzeph.backend.spring.java_spring_demo.entities.users.Client;
import brzeph.backend.spring.java_spring_demo.entities.users.User;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "tb_role")
public class Role implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
    private String description;

    @ManyToMany
    @JoinTable(
            name = "tb_role_permissions",
            joinColumns = @JoinColumn(name = "tb_role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private Set<Permission> permissions = new HashSet<>();

//    @OneToMany(mappedBy = "permissions")
//    private List<Client> clients = new ArrayList<>();

    @OneToMany(mappedBy = "roleClient")
    private List<Client> clients = new ArrayList<>();

    @OneToMany(mappedBy = "roleUser")
    private List<User> users = new ArrayList<>();

    public Role() {
    }

    public Role(Long id, String name, String description, Set<Permission> permissions) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.permissions = permissions;
    }

    public Role(String name){
        this.name = name;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
//                ", clients=" + clients +
//                ", users=" + users +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(id, role.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }
}
