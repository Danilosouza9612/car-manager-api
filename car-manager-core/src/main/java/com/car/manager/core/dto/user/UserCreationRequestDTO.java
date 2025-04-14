package com.car.manager.core.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class UserCreationRequestDTO extends UserDTO{
    @NotBlank @Size(max = 20)
    private String login;

    @NotBlank @Size(max = 20)
    private String password;

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
}