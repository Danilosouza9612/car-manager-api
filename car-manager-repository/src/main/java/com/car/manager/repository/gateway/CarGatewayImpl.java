package com.car.manager.repository.gateway;

import com.car.manager.core.domain.Car;
import com.car.manager.core.gateway.CarGateway;
import com.car.manager.repository.db.CarRepository;
import com.car.manager.repository.mapper.CarMapper;
import com.car.manager.repository.schema.CarSchema;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CarGatewayImpl extends BaseGatewayImpl<Car, CarSchema, Long, CarRepository, CarMapper> implements CarGateway {

    public CarGatewayImpl(CarRepository repository, CarMapper mapper) {
        super(repository, mapper);
    }

    @Override
    public Optional<Car> findById(long id, String login) {
        return repository.findByIdAndLogin(id, login).map(mapper::toDomain);
    }

    @Override
    public List<Car> findAll(int page, int perPage, String login) {
        return repository.findAllByLogin(login, PageRequest.of(page, perPage)).map(mapper::toDomain).stream().toList();
    }

    @Override
    public Car save(Car instance) {
        return mapper.toDomain(repository.save(mapper.toSchema(instance)));
    }

    @Override
    public void delete(long id) {
        repository.deleteById(id);
    }
}
