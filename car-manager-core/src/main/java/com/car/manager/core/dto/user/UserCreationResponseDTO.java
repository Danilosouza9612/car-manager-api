package com.car.manager.core.dto.user;

import com.car.manager.core.dto.car.CarDTO;

import java.util.List;

public class UserCreationResponseDTO extends UserDTO{

    private List<CarDTO> cars;

    public List<CarDTO> getCars() {
        return cars;
    }

    public void setCars(List<CarDTO> cars) {
        this.cars = cars;
    }
}
