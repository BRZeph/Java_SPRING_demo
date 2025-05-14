package brzeph.backend.spring.java_spring_demo.dto.role;

import jakarta.validation.constraints.NotBlank;

import java.util.Objects;

public class RoleDTO {

    private int id;
    @NotBlank(message = "Role must be filled")
    private String name;
    private String description;

    public RoleDTO() {
    }

    public RoleDTO(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public RoleDTO(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "RoleDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoleDTO roleDTO = (RoleDTO) o;
        return id == roleDTO.id && Objects.equals(name, roleDTO.name) && Objects.equals(description, roleDTO.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
