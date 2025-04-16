package com.car.manager.core.gateway;

import com.car.manager.core.domain.Car;
import com.car.manager.core.dto.PageContent;

import java.util.Optional;

public interface CarGateway {
    Optional<Car> findById(long id, String login);
    boolean existsByLicensePlate(String licensePlate);
    PageContent<Car> findAll(int page, int perPage, String login);
    Car save(Car instance);
    void delete(long id);
}
