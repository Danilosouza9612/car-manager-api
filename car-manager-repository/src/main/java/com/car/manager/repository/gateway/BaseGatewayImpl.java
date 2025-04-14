package com.car.manager.repository.gateway;

import com.car.manager.repository.mapper.DomainSchemaMapper;
import org.springframework.data.jpa.repository.JpaRepository;

public class BaseGatewayImpl<D, S, I, R extends JpaRepository<S, I>, M extends DomainSchemaMapper<D, S>> {
    protected final R repository;
    protected final M mapper;

    public BaseGatewayImpl(R repository, M mapper){
        this.repository = repository;
        this.mapper = mapper;
    }
}
