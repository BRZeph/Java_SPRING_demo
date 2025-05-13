package brzeph.backend.spring.java_spring_demo.mappers;

import brzeph.backend.spring.java_spring_demo.dto.role.RoleDTO;
import brzeph.backend.spring.java_spring_demo.entities.permissions.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    Role fromDTO(RoleDTO dto);
    RoleDTO toDTO(Role role);
}
