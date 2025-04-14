package com.car.manager.core.mapper;

import com.car.manager.core.domain.User;
import com.car.manager.core.dto.user.UserCreationRequestDTO;
import com.car.manager.core.dto.user.UserDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserDTOMapper extends DomainDTOMapper<User, UserDTO, UserDTO> {

    User toUserFromCreationDto(UserCreationRequestDTO requestDTO);
}
