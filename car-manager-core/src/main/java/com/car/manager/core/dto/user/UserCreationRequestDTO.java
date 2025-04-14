package com.car.manager.core.dto.user;

import com.car.manager.core.dto.car.CarDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public class UserCreationRequestDTO extends UserDTO{
    @NotBlank @Size(max = 20)
    private String login;

    @NotBlank @Size(max = 20)
    private String password;

    private List<@Valid CarDTO> cars;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<@Valid CarDTO> getCars() {
        return cars;
    }

    public void setCars(List<@Valid CarDTO> cars) {
        this.cars = cars;
    }
}