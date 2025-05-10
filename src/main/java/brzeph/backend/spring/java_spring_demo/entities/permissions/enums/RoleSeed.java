package brzeph.backend.spring.java_spring_demo.entities.permissions.enums;

import brzeph.backend.spring.java_spring_demo.entities.permissions.Permission;
import brzeph.backend.spring.java_spring_demo.entities.permissions.Role;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static brzeph.backend.spring.java_spring_demo.entities.permissions.enums.PermissionSeed.getOrderCRUD;
import static brzeph.backend.spring.java_spring_demo.entities.permissions.enums.PermissionSeed.getUserCRUD;

public enum RoleSeed {
    CLIENT(
            buildSet(List.of(
//                    getOrderCRUD(), getUserCRUD()
            )),
            "Cliente"
    ),
    EMPTY_ROLE(
            buildSet(List.of(

            )),
            "Empty Role"
    ),
    VENDOR(
            buildSet(List.of(
                    getOrderCRUD()//, getUserCRUD()
            )),
            "Vendedor"
    ),
    ADMIN(
            buildSet(List.of(
                    getOrderCRUD(), getUserCRUD()
            )),
            "Administrador"
    );

    private final Set<Permission> permissions;
    private final String description;
    private boolean isSystemDefined;
    private static final HashMap<RoleSeed, Role> persistedRole = new HashMap<>();

    RoleSeed(Set<Permission> permissions, String description) {
        this.permissions = permissions;
        this.description = description;
        this.isSystemDefined = false;
    }

    private static Set<Permission> buildSet(List<Set<Permission>> permissionList) {
        Set<Permission> merged = new HashSet<>();
        for (Set<Permission> permissions : permissionList) {
            merged.addAll(permissions);
        }
        return merged;
    }

    public Role toRole(){
        /*
        TODO: consider ~> create DTO for roleSeed/role (maybe?).
         */
        if (!persistedRole.containsKey(this)) {
            Role role = new Role(null, this.name(), this.getDescription(), this.permissions);
            persistedRole.put(this, role);
            return role;
        } else {
            return persistedRole.get(this);
        }
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public String getDescription() {
        return description;
    }

    public boolean isSystemDefined() {
        return isSystemDefined;
    }

    public void setSystemDefined(boolean systemDefined) {
        isSystemDefined = systemDefined;
    }
}
