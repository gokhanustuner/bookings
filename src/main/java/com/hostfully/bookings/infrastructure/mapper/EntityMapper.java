package com.hostfully.bookings.infrastructure.mapper;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface EntityMapper<T, U> {
    U JPAEntityToDomainEntity(T JPAEntity);

    default List<U> JPAEntityToDomainEntity(List<T> JPAEntities) {
        return JPAEntities.stream().map(this::JPAEntityToDomainEntity).toList();
    }

    T domainEntityToJPAEntity(U domainEntity);

    default List<T> domainEntityToJPAEntity(List<U> domainEntities) {
        return domainEntities.stream().map(this::domainEntityToJPAEntity).toList();
    }
}
