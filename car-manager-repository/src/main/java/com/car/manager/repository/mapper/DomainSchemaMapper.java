package com.car.manager.repository.mapper;

import com.car.manager.core.domain.User;
import com.car.manager.repository.schema.UserSchema;

public interface DomainSchemaMapper<D, S> {
    D toDomain(S schema);
    S toSchema(D domain);
}
