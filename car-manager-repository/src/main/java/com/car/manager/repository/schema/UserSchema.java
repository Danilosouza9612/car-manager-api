package com.car.manager.repository.schema;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class UserSchema {
    @Id
    @GeneratedValue
    private long id;

    @Column(length = 25)
    private String firstName;

    @Column(length = 25)
    private String lastName;

    private URL photoPath;

    @Column(length = 50)
    private String email;

    @Column(length = 25, unique = true)
    private String login;

    @Column
    private LocalDate birthday;

    private String password;

    @Column(length = 11)
    private String phone;

    @OneToMany(mappedBy = "userSchema", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<CarSchema> cars;

    private LocalDateTime lastLogin;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<CarSchema> getCars() {
        return cars;
    }

    public void setCars(List<CarSchema> cars) {
        this.cars = cars;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public URL getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(URL photoPath) {
        this.photoPath = photoPath;
    }
}
