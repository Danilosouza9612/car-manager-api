package com.car.manager.core.dto.car;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CarDTO {

    private int year;

    @NotBlank
    @Size(max = 7, min = 7)
    private String licensePlate;

    @NotBlank
    @Size(max = 20)
    private String model;

    @NotBlank
    @Size(max = 20)
    private String color;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public @NotBlank @Size(max = 7, min = 7) String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(@NotBlank @Size(max = 7, min = 7) String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public @NotBlank @Size(max = 20) String getModel() {
        return model;
    }

    public void setModel(@NotBlank @Size(max = 20) String model) {
        this.model = model;
    }

    public @NotBlank @Size(max = 20) String getColor() {
        return color;
    }

    public void setColor(@NotBlank @Size(max = 20) String color) {
        this.color = color;
    }
}
