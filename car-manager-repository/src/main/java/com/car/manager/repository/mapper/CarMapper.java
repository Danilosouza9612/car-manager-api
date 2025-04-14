package com.car.manager.repository.mapper;

import com.car.manager.core.domain.Car;
import com.car.manager.core.domain.User;
import com.car.manager.repository.db.UserRepository;
import com.car.manager.repository.schema.CarSchema;
import com.car.manager.repository.schema.UserSchema;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class CarMapper implements DomainSchemaMapper<Car, CarSchema> {

    @Autowired
    protected UserRepository userRepository;

    @Mapping(source = "userSchema", target = "user")
    @Mapping(target = "user.cars", ignore = true)
    public abstract Car toDomain(CarSchema requestDto);

    @Mapping(source = "user", target = "userSchema", qualifiedByName = "mapLoginToUserSchema")
    public abstract CarSchema toSchema(Car domain);

    @Named("mapLoginToUserSchema")
    public UserSchema mapLoginToUserSchema(User user){
        return userRepository.findByLogin(user.getLogin()).get();
    }
}
