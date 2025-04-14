package com.car.manager.core.gateway;

import com.car.manager.core.domain.User;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface UserGateway extends CrudGateway<User, Long>{
    Optional<User> findByLogin(String login);
    boolean existsByLogin(String login);
}
