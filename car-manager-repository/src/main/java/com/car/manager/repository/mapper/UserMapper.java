package com.car.manager.repository.mapper;

import com.car.manager.core.domain.User;
import com.car.manager.repository.schema.UserSchema;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper extends DomainSchemaMapper<User, UserSchema>{
    @Mapping(target = "cars", ignore = true)
    User toDomainWithoutCars(UserSchema userSchema);
}
