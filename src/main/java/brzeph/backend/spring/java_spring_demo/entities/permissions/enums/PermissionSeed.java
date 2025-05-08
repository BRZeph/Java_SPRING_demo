package brzeph.backend.spring.java_spring_demo.entities.permissions.enums;

import brzeph.backend.spring.java_spring_demo.entities.permissions.Permission;

import java.util.HashMap;
import java.util.Set;

public enum PermissionSeed {
    CREATE_USER("Criar usuário"),
    READ_USER("Ler dados de usuário"),
    UPDATE_USER("Atualizar usuário"),
    DELETE_USER("Deletar usuário"), //CRUD USER

    CREATE_ORDER("Criar pedido"),
    READ_ORDER("Ler dados de pedido"),
    UPDATE_ORDER("Atualizar pedido"),
    DELETE_ORDER("Deletar pedido"), //CRUD ORDER

    CREATE_CLIENT("[PLACE HOLDER]");

    /* Other PermissionSeed examples:
    GIVE_PERMISSION
    REMOVE_PERMISSION
     */

    private final String description;
    private boolean isSystemDefined;
    private static final HashMap<PermissionSeed, Permission> persistedPermissions = new HashMap<>();

    PermissionSeed(String description) {
        this.description = description;
        this.isSystemDefined = false;
    }

    public static Set<Permission> getUserCRUD(){
        return Set.of(
                CREATE_USER.toPermission(), READ_USER.toPermission(),
                UPDATE_USER.toPermission(), DELETE_USER.toPermission());
    }

    public static Set<Permission> getOrderCRUD(){
        return Set.of(
                CREATE_ORDER.toPermission(), READ_ORDER.toPermission(),
                UPDATE_ORDER.toPermission(), DELETE_ORDER.toPermission());
    }

    public Permission toPermission() {
        /*
        TODO: consider ~> create DTO for permissionSeed/permission (maybe?).
         */
        if (!persistedPermissions.containsKey(this)) {
            Permission perm = new Permission(null, this.name(), this.getDescription());
            persistedPermissions.put(this, perm);
            return perm;
        } else {
            return persistedPermissions.get(this);
        }
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
