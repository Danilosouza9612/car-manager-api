package com.car.manager.core.domain;

import com.car.manager.core.security.PasswordEncryptor;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class User implements Domain<Long>, PhotoAvatarEntity{

    private Long id;

    private String firstName;

    private String lastName;

    private URL photoPath;

    private String email;

    private String login;

    private LocalDate birthday;

    private String password;

    private String phone;

    private List<Car> cars;

    private LocalDateTime createdAt;

    private LocalDateTime lastLogin;

    public User() {}

    public User(long id, String firstName, String lastName, String login, String email, String password, LocalDate birthday, String phone) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.email = email;
        this.password = password;
        this.birthday = birthday;
        this.phone = phone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    public void updateLastLogin(){
        setLastLogin(LocalDateTime.now());
    }

    public URL getPhotoPath() {
        return photoPath ;
    }

    public Optional<URL> getPhotoPathOptional(){
        return Optional.ofNullable(getPhotoPath());
    }

    public void setPhotoPath(URL photoPath) {
        this.photoPath = photoPath;
    }

    public void encryptPassword(PasswordEncryptor encryptor){
        this.setPassword(encryptor.encrypt(this.getPassword()));
    }
}
