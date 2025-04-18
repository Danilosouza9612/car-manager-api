package com.car.manager.core.service;

import com.car.manager.core.Util;
import com.car.manager.core.domain.User;
import com.car.manager.core.dto.PageContent;
import com.car.manager.core.dto.user.*;
import com.car.manager.core.exception.InstanceNotFoundException;
import com.car.manager.core.exception.UniqueValueException;
import com.car.manager.core.gateway.UserGateway;
import com.car.manager.core.mapper.UserDTOMapper;
import com.car.manager.core.security.PasswordEncryptor;
import com.car.manager.core.storage.FileStorage;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.InvalidPropertiesFormatException;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private static final String STORAGE_FOLDER = "Users";

    private static final List<String> VALID_EXTENSIONS = List.of(".png", ".jpg", ".jpeg");

    private final UserGateway gateway;

    private final UserDTOMapper mapper;

    private final PasswordEncryptor passwordEncryptor;

    private final AvatarService avatarService;

    public UserService(
            UserGateway gateway,
            UserDTOMapper mapper,
            PasswordEncryptor passwordEncryptor,
            AvatarService avatarService
    ){
        this.mapper = mapper;
        this.gateway = gateway;
        this.passwordEncryptor = passwordEncryptor;
        this.avatarService = avatarService;
    }

    public UserFullDTO create(UserCreationRequestDTO requestDTO) {
        if(gateway.existsByLogin(requestDTO.getLogin())) throw new UniqueValueException("Login");
        throwUniqueExceptionIfExistsByEmail(requestDTO.getEmail());

        User user = mapper.toUserFromCreationDto(requestDTO);
        user.encryptPassword(passwordEncryptor);

        return mapper.toUserFullDto(gateway.create(user));
    }

    public UserResponseDTO read(Long id) {
        User user = findByIdOrThrowNotFoundException(id);

        return mapper.toDto(user);
    }

    public PageContent<UserResponseDTO> list(int page, int perPage) {
        return gateway.findAll(page, perPage).map(mapper::toDto);
    }

    public UserResponseDTO update(Long id, UserDTO requestDTO) {
        User user = findByIdOrThrowNotFoundException(id);
        if(!user.getEmail().equals(requestDTO.getEmail())) throwUniqueExceptionIfExistsByEmail(requestDTO.getEmail());

        mapper.toDomainUpdate(user, requestDTO);

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

    public URL uploadPhoto(Long id, InputStream inputStream, String extension) throws IOException {
        avatarService.validateExtension(extension);
        User user = findByIdOrThrowNotFoundException(id);
        avatarService.uploadAvatar(user, inputStream, STORAGE_FOLDER, extension);

        return gateway.save(user).getPhotoPath();
    }

    private User findByIdOrThrowNotFoundException(Long id){
        return gateway.findById(id)
                .orElseThrow(InstanceNotFoundException::new);
    }

    private void throwUniqueExceptionIfExistsByEmail(String email){
        if(gateway.existsByEmail(email)) throw new UniqueValueException("Email");
    }
}
