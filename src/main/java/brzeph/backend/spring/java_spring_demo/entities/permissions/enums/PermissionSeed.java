package brzeph.backend.spring.java_spring_demo.entities.permissions.enums;

import brzeph.backend.spring.java_spring_demo.entities.permissions.Permission;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public enum PermissionSeed {
    CREATE_USER("Criar usuário"),
      READ_USER("Ler dados de usuário"),
    UPDATE_USER("Atualizar usuário"),
    DELETE_USER("Deletar usuário"), //CRUD USER

    CREATE_CLIENT("Criar cliente"),
      READ_CLIENT("Ler dados de cliente"),
    UPDATE_CLIENT("Atualizar cliente"),
    DELETE_CLIENT("Deletar cliente"), //CRUD CLIENT

    CREATE_ORDER("Criar pedido"),
      READ_ORDER("Ler dados de pedido"),
    UPDATE_ORDER("Atualizar pedido"),
    DELETE_ORDER("Deletar pedido"), //CRUD ORDER

    CREATE_PRODUCT_CAT("Criar categoria de produto"),
      READ_PRODUCT_CAT("Ler dados de categoria de produto"),
    UPDATE_PRODUCT_CAT("Atualizar categoria de produto"),
    DELETE_PRODUCT_CAT("Deletar categoria de produto"), //CRUD PRODUCT CATEGORY

    CREATE_PRODUCT("Criar produto"),
      READ_PRODUCT("Ler dados de produto"),
    UPDATE_PRODUCT("Atualizar produto"),
    DELETE_PRODUCT("Deletar produto"), //CRUD PRODUCT

    CREATE_SERVER_CONFIG("Criar serverConfig"),
      READ_SERVER_CONFIG("Ler dados de serverConfig"),
    UPDATE_SERVER_CONFIG("Atualizar serverConfig"),
    DELETE_SERVER_CONFIG("Deletar serverConfig"), //CRUD PRODUCT

    READ_SELF("Pegar próprios dados");

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

    public Permission toPermission() {
        if (!persistedPermissions.containsKey(this)) {
            Permission perm = new Permission(null, this.name(), this.getDescription());
            persistedPermissions.put(this, perm);
            return perm;
        } else {
            return persistedPermissions.get(this);
        }
    }

    public static void persist(List<Permission> permissions){
        if (permissions.isEmpty()) return;
        int i = -1;
        for(PermissionSeed seed : PermissionSeed.values()){
            i++;
            if (persistedPermissions.containsKey(seed)) continue;
            persistedPermissions.put(seed, permissions.get(i));
        }
    }

    public static Set<Permission> getUserCRUD(){
        return Set.of(
                CREATE_USER.toPermission(), READ_USER.toPermission(),
                UPDATE_USER.toPermission(), DELETE_USER.toPermission());
    }

    public static Set<Permission> getClientCRUD(){
        return Set.of(
                CREATE_CLIENT.toPermission(), READ_CLIENT.toPermission(),
                UPDATE_CLIENT.toPermission(), DELETE_CLIENT.toPermission());
    }

    public static Set<Permission> getOrderCRUD(){
        return Set.of(
                CREATE_ORDER.toPermission(), READ_ORDER.toPermission(),
                UPDATE_ORDER.toPermission(), DELETE_ORDER.toPermission());
    }

    public static Set<Permission> getProductCategory(){
        return Set.of(
                CREATE_PRODUCT_CAT.toPermission(), READ_PRODUCT_CAT.toPermission(),
                UPDATE_PRODUCT_CAT.toPermission(), DELETE_PRODUCT_CAT.toPermission());
    }

    public static Set<Permission> getProduct(){
        return Set.of(
                CREATE_PRODUCT.toPermission(), READ_PRODUCT.toPermission(),
                UPDATE_PRODUCT.toPermission(), DELETE_PRODUCT.toPermission());
    }

    public static Set<Permission> getServerConfig(){
        return Set.of(
                CREATE_SERVER_CONFIG.toPermission(), READ_SERVER_CONFIG.toPermission(),
                UPDATE_SERVER_CONFIG.toPermission(), DELETE_SERVER_CONFIG.toPermission());
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
