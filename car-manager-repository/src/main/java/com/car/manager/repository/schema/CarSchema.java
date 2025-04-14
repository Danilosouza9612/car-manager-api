package com.car.manager.repository.schema;

import jakarta.persistence.*;

@Entity
public class CarSchema {
    @Id
    @GeneratedValue
    private long id;

    @Column(name = "car_year")
    private int year;

    @Column(length = 7)
    private String licensePlate;

    @Column(length = 20)
    private String model;

    @Column(length = 20)
    private String color;

    @ManyToOne
    private UserSchema userSchema;

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public UserSchema getUserSchema() {
        return userSchema;
    }

    public void setUserSchema(UserSchema userSchema) {
        this.userSchema = userSchema;
    }
}
