package com.car.manager.core.dto.user;

import java.time.LocalDateTime;

public class MeDTO extends UserFullDTO{
    private LocalDateTime lastLogin;

    private LocalDateTime createdAt;

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
}
