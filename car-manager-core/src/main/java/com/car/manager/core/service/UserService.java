package com.car.manager.core.service;

import com.car.manager.core.domain.User;
import com.car.manager.core.gateway.UserGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserGateway userGateway;
    @Autowired
    public UserService(UserGateway userGateway){
        this.userGateway = userGateway;
    }

    public Optional<User> findByLogin(String login){
        return userGateway.findByLogin(login);
    }
}
