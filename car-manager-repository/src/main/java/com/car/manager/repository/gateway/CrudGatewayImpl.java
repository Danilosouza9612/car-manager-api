package com.car.manager.repository.gateway;

import com.car.manager.core.dto.PageContent;
import com.car.manager.core.gateway.CrudGateway;
import com.car.manager.repository.mapper.DomainSchemaMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public abstract class CrudGatewayImpl<D, S, I, M extends DomainSchemaMapper<D, S>, R extends JpaRepository<S, I>> extends BaseGatewayImpl<D, S, I, R, M> implements CrudGateway<D, I> {

    public CrudGatewayImpl(R repository, M mapper) {
        super(repository, mapper);
    }

    @Override
    public Optional<D> findById(I id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public PageContent<D> findAll(int page, int perPage) {
        return this.ToPageContent(repository.findAll(PageRequest.of(page, perPage)));
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

    @Override
    List<D> itemsFromJpaPage(Page<S> page) {
        return page.map(mapper::toDomain).toList();
    }
}
