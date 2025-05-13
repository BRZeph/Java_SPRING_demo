package brzeph.backend.spring.java_spring_demo.mappers;

import brzeph.backend.spring.java_spring_demo.dto.client.ClientCreateDTO;
import brzeph.backend.spring.java_spring_demo.dto.client.ClientReadDTO;
import brzeph.backend.spring.java_spring_demo.dto.client.ClientUpdateDTO;
import brzeph.backend.spring.java_spring_demo.dto.user.UserCreateDTO;
import brzeph.backend.spring.java_spring_demo.dto.user.UserReadDTO;
import brzeph.backend.spring.java_spring_demo.dto.user.UserUpdateDTO;
import brzeph.backend.spring.java_spring_demo.entities.users.Client;
import brzeph.backend.spring.java_spring_demo.entities.users.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    // --------- Create ---------
    Client fromCreateDTO(ClientCreateDTO dto);
    ClientCreateDTO toCreateDTO(Client obj);

    // --------- Read ---------

    ClientReadDTO toReadDTO(Client obj);

    // --------- Update ---------
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateClientFromDTO(ClientUpdateDTO obj, @MappingTarget Client client);
}