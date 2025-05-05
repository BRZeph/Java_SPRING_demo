package brzeph.backend.spring.java_spring_demo.entities.permissions.pk;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PermissionGroupLinkPK implements Serializable {

    @Column(name = "group_id")
    private Long groupId;

    @Column(name = "permission_id")
    private Long permissionId;

    public PermissionGroupLinkPK() {
    }

    public PermissionGroupLinkPK(Long groupId, Long permissionId) {
        this.groupId = groupId;
        this.permissionId = permissionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PermissionGroupLinkPK that = (PermissionGroupLinkPK) o;
        return Objects.equals(groupId, that.groupId) && Objects.equals(permissionId, that.permissionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupId, permissionId);
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }
}
