package com.car.manager.repository.gateway;

import com.car.manager.core.domain.User;
import com.car.manager.core.dto.PageContent;
import com.car.manager.core.gateway.UserGateway;
import com.car.manager.repository.db.UserRepository;
import com.car.manager.repository.mapper.UserMapper;
import com.car.manager.repository.schema.UserSchema;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserGatewayImpl extends BaseGatewayImpl<User, UserSchema, Long, UserRepository, UserMapper> implements UserGateway {

    public UserGatewayImpl(UserRepository repository, UserMapper mapper) {
        super(repository, mapper);
    }

    @Override
    public Optional<User> findById(Long id) {
        return repository.findById(id).map(mapper::toDomainWithoutCars);
    }

    @Override
    public PageContent<User> findAll(int page, int perPage) {
        return this.ToPageContent(repository.findAll(PageRequest.of(page, perPage)));
    }

    @Override
    public User create(User instance) {
        UserSchema userSchema = mapper.toSchema(instance);
        userSchema.getCars().forEach(carSchema -> {
            carSchema.setUserSchema(userSchema);
        });
        return mapper.toDomain(repository.save(userSchema));
    }

    @Override
    public User save(User instance) {
        return mapper.toDomainWithoutCars(repository.save(mapper.toSchema(instance)));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return repository.findByLogin(login).map(mapper::toDomainWithoutCars);
    }

    @Override
    public Optional<User> findFullByLogin(String login) {
        return repository.findByLogin(login).map(mapper::toDomain);
    }

    @Override
    public boolean existsByLogin(String login) {
        return repository.existsByLogin(login);
    }

    @Override
    List<User> itemsFromJpaPage(Page<UserSchema> page) {
        return page.map(mapper::toDomainWithoutCars).toList();
    }
}
