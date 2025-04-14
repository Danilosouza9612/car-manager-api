package com.car.manager.core.service;

import com.car.manager.core.domain.User;
import com.car.manager.core.dto.user.UserCreationRequestDTO;
import com.car.manager.core.dto.user.UserDTO;
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

    public UserDTO createUser(UserCreationRequestDTO requestDTO) {
        if(gateway.existsByLogin(requestDTO.getLogin())) throw new UniqueValueException("login");
        return mapper.toDto(gateway.save(mapper.toUserFromCreationDto(requestDTO)));
    }

    public UserDTO getUserById(Long id) {
        User user = findByIdOrThrowNotFoundException(id);

        return mapper.toDto(user);
    }

    public List<UserDTO> getAllUsers(int page, int perPage) {
        return gateway.findAll(page, perPage).stream().map(mapper::toDto).toList();
    }

    public UserDTO updateUser(Long id, UserDTO requestDTO) {
        findByIdOrThrowNotFoundException(id);
        User user = mapper.toDomain(requestDTO);
        user.setId(id);

        return mapper.toDto(gateway.save(user));
    }

    public void deleteUser(Long id) {
        findByIdOrThrowNotFoundException(id);

        gateway.delete(id);
    }

    public Optional<User> findByLogin(String login){
        return gateway.findByLogin(login);
    }

    private User findByIdOrThrowNotFoundException(Long id){
        return gateway.findById(id)
                .orElseThrow(InstanceNotFoundException::new);
    }
}
