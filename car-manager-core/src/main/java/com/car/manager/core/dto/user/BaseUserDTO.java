package com.car.manager.core.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public interface BaseUserDTO {
    @NotBlank
    @Size(max = 25)
    String firstName();

    @NotBlank @Size(max = 25)
    String lastName();

    @Email
    @NotBlank @Size(max = 30)
    String email();

    @NotBlank @Size(max = 20)
    LocalDate birthday();

    @NotBlank @Size(max = 11)
    String phone();
}
