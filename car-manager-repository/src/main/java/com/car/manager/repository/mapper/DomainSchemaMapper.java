package com.car.manager.repository.mapper;

public interface DomainSchemaMapper<D, S> {
    D toDomain(S schema);
    S toSchema(D domain);
}
