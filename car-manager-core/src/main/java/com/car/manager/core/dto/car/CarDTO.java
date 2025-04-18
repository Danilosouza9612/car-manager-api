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

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor( String color) {
        this.color = color;
    }
}
