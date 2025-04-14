package com.car.manager.core.mapper;

import org.mapstruct.Mapping;

public interface DomainDTOMapper<D, I, O>  {

    D toDomain(I requestDto);

    O toDto(D instance);
}
