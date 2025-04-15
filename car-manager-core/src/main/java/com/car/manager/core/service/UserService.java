package com.car.manager.core.service;

import com.car.manager.core.domain.User;
import com.car.manager.core.dto.PageContent;
import com.car.manager.core.dto.user.*;
import com.car.manager.core.exception.InstanceNotFoundException;
import com.car.manager.core.exception.UniqueValueException;
import com.car.manager.core.gateway.UserGateway;
import com.car.manager.core.mapper.UserDTOMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserGateway gateway;

    private final UserDTOMapper mapper;

    public UserService(UserGateway gateway, UserDTOMapper mapper){
        this.mapper = mapper;
        this.gateway = gateway;
    }

    public UserFullDTO create(UserCreationRequestDTO requestDTO) {
        if(gateway.existsByLogin(requestDTO.getLogin())) throw new UniqueValueException("login");
        return mapper.toUserFullDto(gateway.create(mapper.toUserFromCreationDto(requestDTO)));
    }

    public UserResponseDTO read(Long id) {
        User user = findByIdOrThrowNotFoundException(id);

        return mapper.toDto(user);
    }

    public PageContent<UserResponseDTO> list(int page, int perPage) {
        return gateway.findAll(page, perPage).map(mapper::toDto);
    }

    public UserResponseDTO update(Long id, UserDTO requestDTO) {
        findByIdOrThrowNotFoundException(id);
        User user = mapper.toDomain(requestDTO);
        user.setId(id);

        return mapper.toDto(gateway.save(user));
    }

    public void delete(Long id) {
        findByIdOrThrowNotFoundException(id);

        gateway.delete(id);
    }

    public Optional<User> findByLogin(String login){
        return gateway.findByLogin(login);
    }

    public void updateLogin(String login){
        User user = gateway.findByLogin(login).orElseThrow(InstanceNotFoundException::new);
        user.updateLastLogin();
        gateway.save(user);
    }

    public MeDTO me(String login){
        return gateway.findFullByLogin(login).map(mapper::toMeDto).orElseThrow(InstanceNotFoundException::new);
    }

    private User findByIdOrThrowNotFoundException(Long id){
        return gateway.findById(id)
                .orElseThrow(InstanceNotFoundException::new);
    }
}
