package brzeph.backend.spring.java_spring_demo.mappers;

import brzeph.backend.spring.java_spring_demo.dto.user.UserCreateDTO;
import brzeph.backend.spring.java_spring_demo.dto.user.UserReadDTO;
import brzeph.backend.spring.java_spring_demo.dto.user.UserUpdateDTO;
import brzeph.backend.spring.java_spring_demo.entities.users.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface UserMapper {

    // --------- Create ---------
    User fromCreateDTO(UserCreateDTO dto);
    UserCreateDTO toCreateDTO(User user);

    // --------- Read ---------
    UserReadDTO toReadDTO(User user);

    // --------- Update ---------
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserFromDTO(UserUpdateDTO dto, @MappingTarget User user);
}