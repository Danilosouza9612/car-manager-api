package com.car.manager.core.gateway;

import com.car.manager.core.domain.User;

import java.util.Optional;

public interface UserGateway {
    Optional<User> findByLogin(String login);
}
