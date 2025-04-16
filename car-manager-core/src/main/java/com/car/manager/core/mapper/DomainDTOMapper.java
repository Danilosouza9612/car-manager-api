package com.car.manager.core.mapper;

import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

public interface DomainDTOMapper<D, I, O>  {

    D toDomain(I requestDto);

    O toDto(D instance);

    void toDomainUpdate(@MappingTarget D instance, I requestDto);
}
