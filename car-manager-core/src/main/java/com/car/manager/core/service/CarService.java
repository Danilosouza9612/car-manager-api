package com.car.manager.core.service;

import com.car.manager.core.domain.Car;
import com.car.manager.core.domain.User;
import com.car.manager.core.dto.car.CarDTO;
import com.car.manager.core.dto.car.CarResponseDTO;
import com.car.manager.core.exception.InstanceNotFoundException;
import com.car.manager.core.gateway.CarGateway;
import com.car.manager.core.mapper.CarDTOMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {
    protected CarGateway gateway;

    protected CarDTOMapper mapper;

    public CarService(CarGateway gateway, CarDTOMapper mapper){
        this.gateway = gateway;
        this.mapper = mapper;
    }

    public List<CarResponseDTO> list(int page, int perPage, String login){
        return gateway.findAll(page, perPage, login).stream().map(mapper::toDto).toList();
    }

    public CarResponseDTO create(CarDTO requestDto, String login){
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
        if(gateway.findById(id, login).isEmpty()) throw new InstanceNotFoundException();

        User user = new User();
        user.setLogin(login);
        Car instance = mapper.toDomain(requestDto);
        instance.setUser(user);
        instance.setId(id);

        return mapper.toDto(gateway.save(instance));
    }

    public void delete(long id, String login){
        if(gateway.findById(id, login).isEmpty()) throw new InstanceNotFoundException();
        gateway.delete(id);
    }
}
