package com.car.manager.repository.gateway;

import com.car.manager.core.domain.User;
import com.car.manager.core.gateway.UserGateway;
import com.car.manager.repository.db.UserRepository;
import com.car.manager.repository.mapper.UserMapper;
import com.car.manager.repository.schema.UserSchema;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserGatewayImpl extends CrudGatewayImpl<User, UserSchema, Long, UserMapper, UserRepository> implements UserGateway {

    public UserGatewayImpl(UserRepository repository, UserMapper mapper) {
        super(repository, mapper);
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return repository.findByLogin(login).map(mapper::toDomain);
    }

    @Override
    public boolean existsByLogin(String login) {
        return repository.existsByLogin(login);
    }
}
