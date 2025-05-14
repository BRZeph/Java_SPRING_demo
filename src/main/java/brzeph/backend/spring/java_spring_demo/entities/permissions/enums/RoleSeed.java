package brzeph.backend.spring.java_spring_demo.entities.permissions.enums;

import brzeph.backend.spring.java_spring_demo.entities.permissions.Permission;
import brzeph.backend.spring.java_spring_demo.entities.permissions.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static brzeph.backend.spring.java_spring_demo.entities.permissions.enums.PermissionSeed.*;

public enum RoleSeed {
    CLIENT(
            buildSet(List.of(
                    Set.of(READ_SELF.toPermission())
            )),
            "Cliente"
    ),
    EMPTY_ROLE(
            buildSet(List.of(
                    Set.of(READ_SELF.toPermission())
            )),
            "Empty Role"
    ),
    VENDOR(
            buildSet(List.of(
                    Set.of(READ_SELF.toPermission()),
                    getOrderCRUD()
            )),
            "Vendedor"
    ),
    ADMIN(
            buildSet(List.of(
                    Set.of(READ_SELF.toPermission()),
                    getUserCRUD(), getClientCRUD(), getOrderCRUD(), getProductCategory(), getProduct(), getServerConfig()
            )),
            "Administrador"
    );

    private static final Logger logger = LoggerFactory.getLogger(RoleSeed.class.getName());

    private final Set<Permission> permissions;
    private final String description;
    private boolean isSystemDefined;
    private static final HashMap<RoleSeed, Role> persistedRole = new HashMap<>();

    RoleSeed(Set<Permission> permissions, String description) {
        this.permissions = permissions;
        this.description = description;
        this.isSystemDefined = false;
    }

    public static RoleSeed getRoleByName(String name){
        RoleSeed role = null;
        for(RoleSeed roleSeed : RoleSeed.values()){
            if (roleSeed.name().equals(name)){
                role = roleSeed;
            }
        }
        return role;
    }

    public static Set<Permission> getAllPermissions(){
        Set<Permission> perms = new HashSet<>();
        for (PermissionSeed perm : PermissionSeed.values()) {
            perms.add(perm.toPermission());
        }
        return perms;
    }

    private static Set<Permission> buildSet(List<Set<Permission>> permissionList) {
        Set<Permission> merged = new HashSet<>();
        for (Set<Permission> permissions : permissionList) {
            merged.addAll(permissions);
        }
        return merged;
    }

    public Role toRole(){
        if (!persistedRole.containsKey(this)) {
            Role role = new Role(null, this.name(), this.getDescription(), this.permissions);
            persistedRole.put(this, role);
            return role;
        } else {
            return persistedRole.get(this);
        }
    }

    public static void persist(List<Role> roles){
        if (roles.isEmpty()) return;
        int i = -1;
        persistedRole.clear();
        for(RoleSeed seed : RoleSeed.values()){
            i++;
            if (persistedRole.containsKey(seed)) continue;
            persistedRole.put(seed, roles.get(i));
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
