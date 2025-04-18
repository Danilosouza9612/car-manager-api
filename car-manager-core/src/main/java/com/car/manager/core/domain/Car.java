package com.car.manager.core.domain;

import java.net.URL;
import java.util.Optional;

public class Car implements PhotoAvatarEntity{
    private Long id;

    private int year;

    private URL photoPath;

    private String licensePlate;

    private String model;

    private String color;

    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public void setColor(String color) {
        this.color = color;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public URL getPhotoPath(){
        return photoPath;
    }

    public void setPhotoPath(URL photoPath) {
        this.photoPath = photoPath;
    }

    @Override
    public Optional<URL> getPhotoPathOptional() {
        return Optional.ofNullable(getPhotoPath());
    }
}
