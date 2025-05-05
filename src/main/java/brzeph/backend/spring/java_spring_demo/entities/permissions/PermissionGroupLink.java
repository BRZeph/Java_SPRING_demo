package brzeph.backend.spring.java_spring_demo.entities.permissions;

import brzeph.backend.spring.java_spring_demo.entities.permissions.pk.PermissionGroupLinkPK;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "tb_perms_link")
public class PermissionGroupLink  implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private PermissionGroupLinkPK id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("groupId")
    @JoinColumn(name = "group_id")
    private PermissionGroup group;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("permissionId")
    @JoinColumn(name = "permission_id")
    private Permission permission;

    public PermissionGroupLink() {
    }

    public PermissionGroupLink(PermissionGroupLinkPK id, PermissionGroup group, Permission permission) {
        this.id = id;
        this.group = group;
        this.permission = permission;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PermissionGroupLink that = (PermissionGroupLink) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public PermissionGroupLinkPK getId() {
        return id;
    }

    public void setId(PermissionGroupLinkPK id) {
        this.id = id;
    }

    public PermissionGroup getGroup() {
        return group;
    }

    public void setGroup(PermissionGroup group) {
        this.group = group;
    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }
}
