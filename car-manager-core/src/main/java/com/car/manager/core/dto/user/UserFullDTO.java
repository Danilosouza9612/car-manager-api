package com.car.manager.core.dto.user;

import com.car.manager.core.dto.car.CarDTO;
import com.car.manager.core.dto.car.CarResponseDTO;

import java.net.URL;
import java.util.List;

public class UserFullDTO extends UserDTO{

    private List<CarResponseDTO> cars;

    private URL photoPath;

    public List<CarResponseDTO> getCars() {
        return cars;
    }

    public void setCars(List<CarResponseDTO> cars) {
        this.cars = cars;
    }

    public URL getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(URL photoPath) {
        this.photoPath = photoPath;
    }
}
