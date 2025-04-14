package com.car.manager.core.dto.user;

import java.time.LocalDate;

public class MeDTO extends UserFullDTO{
    private LocalDate lastLogin;

    private LocalDate createdAt;

    public LocalDate getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDate lastLogin) {
        this.lastLogin = lastLogin;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }
}
