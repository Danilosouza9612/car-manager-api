package com.car.manager.core.service;

import com.car.manager.core.domain.Domain;
import com.car.manager.core.exception.InstanceNotFoundException;
import com.car.manager.core.gateway.CrudGateway;
import com.car.manager.core.mapper.DomainDTOMapper;

import java.util.List;

public abstract class CrudService<D extends Domain<ID>, ID, G extends CrudGateway<D, ID>, I, O, M extends DomainDTOMapper<D, I, O>> {

    protected G gateway;

    protected M mapper;

    public CrudService(G gateway, M mapper){
        this.gateway = gateway;
        this.mapper = mapper;
    }

    public List<O> list(int page, int perPage){
        return gateway.findAll(page, perPage).stream().map(mapper::toDto).toList();
    }

    public O create(I requestDto){
        return mapper.toDto(gateway.save(mapper.toDomain(requestDto)));
    }

    public O read(ID id){
        return gateway.findById(id).map(mapper::toDto).orElseThrow(InstanceNotFoundException::new);
    }

    public O update(ID id, I requestDto){
        if(gateway.findById(id).isEmpty()) throw new InstanceNotFoundException();
        D instance = mapper.toDomain(requestDto);
        instance.setId(id);
        return mapper.toDto(gateway.save(instance));
    }

    public void delete(ID id){
        gateway.delete(id);
    }
}
