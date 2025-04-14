package com.car.manager.repository.gateway;

import com.car.manager.core.gateway.CrudGateway;
import com.car.manager.repository.mapper.DomainSchemaMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public abstract class CrudGatewayImpl<D, S, I, M extends DomainSchemaMapper<D, S>, R extends JpaRepository<S, I>> implements CrudGateway<D, I> {
    protected final R repository;
    protected final M mapper;

    public CrudGatewayImpl(R repository, M mapper){
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Optional<D> findById(I id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<D> findAll(int page, int perPage) {
        return repository.findAll(PageRequest.of(page, perPage))
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public D create(D instance) {
        return mapper.toDomain(repository.save(mapper.toSchema(instance)));
    }

    @Override
    public D save(D instance) {
        return mapper.toDomain(repository.save(mapper.toSchema(instance)));
    }

    @Override
    public void delete(I id) {
        repository.deleteById(id);
    }
}
