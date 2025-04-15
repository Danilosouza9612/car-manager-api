package com.car.manager.repository.gateway;

import com.car.manager.core.domain.Car;
import com.car.manager.core.dto.PageContent;
import com.car.manager.core.gateway.CarGateway;
import com.car.manager.repository.db.CarRepository;
import com.car.manager.repository.mapper.CarMapper;
import com.car.manager.repository.schema.CarSchema;
import org.springframework.data.domain.Page;
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
    public PageContent<Car> findAll(int page, int perPage, String login) {
        return ToPageContent(repository.findAllByLogin(login, PageRequest.of(page, perPage)));
    }

    @Override
    public Car save(Car instance) {
        return mapper.toDomain(repository.save(mapper.toSchema(instance)));
    }

    @Override
    public void delete(long id) {
        repository.deleteById(id);
    }

    @Override
    List<Car> itemsFromJpaPage(Page<CarSchema> page) {
        return page.map(mapper::toDomain).toList();
    }
}
