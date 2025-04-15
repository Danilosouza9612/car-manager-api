package com.car.manager.repository.gateway;

import com.car.manager.core.dto.PageContent;
import com.car.manager.repository.mapper.DomainSchemaMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public abstract class BaseGatewayImpl<D, S, I, R extends JpaRepository<S, I>, M extends DomainSchemaMapper<D, S>> {
    protected final R repository;
    protected final M mapper;

    public BaseGatewayImpl(R repository, M mapper){
        this.repository = repository;
        this.mapper = mapper;
    }

    protected PageContent<D> ToPageContent(Page<S> page){
        return new PageContent<>(
                page.getNumber(),
                page.getTotalElements(),
                page.getTotalPages(),
                itemsFromJpaPage(page)
        );
    }

    abstract List<D> itemsFromJpaPage(Page<S> page);
}
