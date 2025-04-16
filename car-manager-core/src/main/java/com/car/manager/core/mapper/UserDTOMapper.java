package com.car.manager.core.mapper;

import com.car.manager.core.domain.User;
import com.car.manager.core.dto.user.*;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserDTOMapper extends DomainDTOMapper<User, UserDTO, UserResponseDTO> {

    User toUserFromCreationDto(UserCreationRequestDTO requestDTO);

    UserCreationRequestDTO toUserCreationRequestDTO(User userCreationRequestDTO);

    UserFullDTO toUserFullDto(User user);

    MeDTO toMeDto(User user);
}
