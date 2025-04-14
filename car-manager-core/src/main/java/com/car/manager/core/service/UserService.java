package com.car.manager.core.service;

import com.car.manager.core.domain.User;
import com.car.manager.core.dto.user.UserCreationRequestDTO;
import com.car.manager.core.dto.user.UserDTO;
import com.car.manager.core.dto.user.UserFullDTO;
import com.car.manager.core.exception.InstanceNotFoundException;
import com.car.manager.core.exception.UniqueValueException;
import com.car.manager.core.gateway.UserGateway;
import com.car.manager.core.mapper.UserDTOMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserGateway gateway;

    private final UserDTOMapper mapper;

    public UserService(UserGateway gateway, UserDTOMapper mapper){
        this.mapper = mapper;
        this.gateway = gateway;
    }

    public UserDTO create(UserCreationRequestDTO requestDTO) {
        if(gateway.existsByLogin(requestDTO.getLogin())) throw new UniqueValueException("login");
        return mapper.toUserFullDto(gateway.create(mapper.toUserFromCreationDto(requestDTO)));
    }

    public UserDTO read(Long id) {
        User user = findByIdOrThrowNotFoundException(id);

        return mapper.toDto(user);
    }

    public List<UserDTO> list(int page, int perPage) {
        return gateway.findAll(page, perPage).stream().map(mapper::toDto).toList();
    }

    public UserDTO update(Long id, UserDTO requestDTO) {
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

    public UserFullDTO me(String login){
        return gateway.findFullByLogin(login).map(mapper::toUserFullDto).orElseThrow(InstanceNotFoundException::new);
    }

    private User findByIdOrThrowNotFoundException(Long id){
        return gateway.findById(id)
                .orElseThrow(InstanceNotFoundException::new);
    }
}
