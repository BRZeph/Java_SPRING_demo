package brzeph.backend.spring.java_spring_demo.mappers;

import brzeph.backend.spring.java_spring_demo.dto.user.UserCreateDTO;
import brzeph.backend.spring.java_spring_demo.dto.user.UserReadDTO;
import brzeph.backend.spring.java_spring_demo.dto.user.UserUpdateDTO;
import brzeph.backend.spring.java_spring_demo.entities.users.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = RoleMapper.class)
public interface UserMapper {

    // --------- Create ---------
    User fromCreateDTO(UserCreateDTO dto);
    UserCreateDTO toCreateDTO(User user);

    // --------- Read ---------
    @Mapping(source = "roleUser", target = "roleUser")
    UserReadDTO toReadDTO(User user);

    // --------- Update ---------
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserFromDTO(UserUpdateDTO dto, @MappingTarget User user);
}