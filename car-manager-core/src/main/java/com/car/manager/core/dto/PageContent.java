package com.car.manager.core.dto;

import java.util.List;
import java.util.function.Function;

public record PageContent<T>(
        int page,
        long total,
        int totalPages,
        List<T> items
){
    public <C> PageContent<C> map(Function<T, C> function){
        return new PageContent<C>(page, total, totalPages, items.stream().map(function).toList());
    }
}