package com.car.manager.repository.gateway;

import com.car.manager.core.domain.User;
import com.car.manager.core.gateway.UserGateway;
import com.car.manager.repository.db.UserRepository;
import com.car.manager.repository.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserGatewayImpl implements UserGateway {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Autowired
    public UserGatewayImpl(UserRepository userRepository, UserMapper userMapper){
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return userRepository.findByLogin(login).map(userMapper::toDomain);
    }
}
