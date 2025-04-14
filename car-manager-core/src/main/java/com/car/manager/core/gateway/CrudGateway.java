package com.car.manager.core.gateway;

import com.car.manager.core.domain.User;

import java.util.List;
import java.util.Optional;

public interface CrudGateway<T, I> {
    Optional<T> findById(I id);
    List<T> findAll(int page, int perPage);
    T create(T instance);
    T save(T instance);
    void delete(I id);
}
