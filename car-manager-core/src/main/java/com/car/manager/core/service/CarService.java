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
import org.springframework.stereotype.Service;

@Service
public class CarService {
    protected CarGateway gateway;

    protected CarDTOMapper mapper;

    public CarService(CarGateway gateway, CarDTOMapper mapper){
        this.gateway = gateway;
        this.mapper = mapper;
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
        throwInstanceNotFound(id, login);
        throwUniqueValueException(requestDto.getLicensePlate());

        User user = new User();
        user.setLogin(login);
        Car instance = mapper.toDomain(requestDto);
        instance.setUser(user);
        instance.setId(id);

        return mapper.toDto(gateway.save(instance));
    }

    public void delete(long id, String login){
        throwInstanceNotFound(id, login);
        gateway.delete(id);
    }

    private void throwInstanceNotFound(long id, String login){
        if(gateway.findById(id, login).isEmpty()) throw new InstanceNotFoundException();
    }

    private void throwUniqueValueException(String licensePlate){
        if(gateway.existsByLicensePlate(licensePlate)) throw new UniqueValueException("License plate");
    }
}
