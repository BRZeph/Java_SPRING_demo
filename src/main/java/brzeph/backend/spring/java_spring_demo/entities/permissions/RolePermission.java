package brzeph.backend.spring.java_spring_demo.entities.permissions;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "tb_role_permission_summary")
public class RolePermission implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "role_name", nullable = false)
    private String roleName;

    @Column(name = "role_description")
    private String roleDescription;

    @Column(name = "permission_name", nullable = false)
    private String permissionName;

    @Column(name = "permission_description")
    private String permissionDescription;

    public RolePermission() {
    }

    public RolePermission(Long id, Role role, Permission permission) {
        this.id = id;
        this.roleName = role.getName();
        this.roleDescription = role.getDescription();
        this.permissionName = permission.getName();
        this.permissionDescription = permission.getDescription();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        RolePermission that = (RolePermission) o;
        return Objects.equals(id, that.id) && Objects.equals(roleName, that.roleName) && Objects.equals(permissionName, that.permissionName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, roleName, permissionName);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getPermissionDescription() {
        return permissionDescription;
    }

    public void setPermissionDescription(String permissionDescription) {
        this.permissionDescription = permissionDescription;
    }
}
