package brzeph.backend.spring.java_spring_demo.mappers;

import brzeph.backend.spring.java_spring_demo.dto.client.ClientCreateDTO;
import brzeph.backend.spring.java_spring_demo.dto.client.ClientReadDTO;
import brzeph.backend.spring.java_spring_demo.dto.client.ClientUpdateDTO;
import brzeph.backend.spring.java_spring_demo.entities.users.Client;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", uses = RoleMapper.class)
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