package com.car.manager.core.mapper;

import com.car.manager.core.domain.Car;
import com.car.manager.core.dto.car.CarDTO;
import com.car.manager.core.dto.car.CarResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CarDTOMapper extends DomainDTOMapper<Car, CarDTO, CarResponseDTO> {
}
