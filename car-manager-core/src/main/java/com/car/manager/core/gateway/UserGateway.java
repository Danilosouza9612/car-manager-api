package com.car.manager.core.gateway;

import com.car.manager.core.domain.User;

import java.util.Optional;

public interface UserGateway extends CrudGateway<User, Long>{
    Optional<User> findByLogin(String login);
    Optional<User> findFullByLogin(String login);
    boolean existsByLogin(String login);
}
