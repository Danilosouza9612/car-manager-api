package com.car.manager.core.service;

import com.car.manager.core.domain.Car;
import com.car.manager.core.domain.User;
import com.car.manager.core.dto.PageContent;
import com.car.manager.core.dto.car.CarDTO;
import com.car.manager.core.dto.car.CarResponseDTO;
import com.car.manager.core.exception.InstanceNotFoundException;
import com.car.manager.core.exception.UniqueValueException;
import com.car.manager.core.gateway.CarGateway;
import com.car.manager.core.mapper.CarDTOMapper;
import com.car.manager.core.storage.FileStorage;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

@Service
public class CarService {
    private static final String STORAGE_FOLDER = "cars";

    private final CarGateway gateway;

    private final CarDTOMapper mapper;


    private final AvatarService avatarService;

    public CarService(CarGateway gateway, CarDTOMapper mapper, AvatarService avatarService){
        this.gateway = gateway;
        this.mapper = mapper;
        this.avatarService = avatarService;
    }

    public PageContent<CarResponseDTO> list(int page, int perPage, String login){
        return gateway.findAll(page, perPage, login).map(mapper::toDto);
    }

    public CarResponseDTO create(CarDTO requestDto, String login){
        throwUniqueValueException(requestDto.getLicensePlate());
        Car car = mapper.toDomain(requestDto);
        User user = new User();
        user.setLogin(login);
        car.setUser(user);

        return mapper.toDto(gateway.save(car));
    }

    public CarResponseDTO read(long id, String login){
        return gateway.findById(id, login).map(mapper::toDto).orElseThrow(InstanceNotFoundException::new);
    }

    public CarResponseDTO update(long id, CarDTO requestDto, String login){
        findByLoginAndIdorThrowInstanceNotFound(id, login);
        throwUniqueValueException(requestDto.getLicensePlate());

        User user = new User();
        user.setLogin(login);
        Car instance = mapper.toDomain(requestDto);
        instance.setUser(user);
        instance.setId(id);

        return mapper.toDto(gateway.save(instance));
    }

    public void delete(long id, String login){
        findByLoginAndIdorThrowInstanceNotFound(id, login);
        gateway.delete(id);
    }

    public URL uploadPhoto(Long id, String login, InputStream inputStream, String extension) throws IOException {
        avatarService.validateExtension(extension);
        Car car = findByLoginAndIdorThrowInstanceNotFound(id, login);
        avatarService.uploadAvatar(car, inputStream, STORAGE_FOLDER, extension);

        return gateway.save(car).getPhotoPath();
    }

    private Car findByLoginAndIdorThrowInstanceNotFound(long id, String login){
        return gateway.findById(id, login).orElseThrow(InstanceNotFoundException::new);
    }

    private void throwUniqueValueException(String licensePlate){
        if(gateway.existsByLicensePlate(licensePlate)) throw new UniqueValueException("License plate");
    }
}
