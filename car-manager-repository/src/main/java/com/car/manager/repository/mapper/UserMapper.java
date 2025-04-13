package com.car.manager.repository.mapper;

import com.car.manager.core.domain.User;
import com.car.manager.repository.schema.UserSchema;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends DomainSchemaMapper<User, UserSchema>{
}
