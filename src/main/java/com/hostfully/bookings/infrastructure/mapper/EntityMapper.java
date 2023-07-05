package com.hostfully.bookings.infrastructure.mapper;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface EntityMapper<T, U> {
    U JPAEntityToDomainEntity(T JPAEntity);

    List<U> JPAEntityToDomainEntity(List<T> JPAEntities);

    T domainEntityToJPAEntity(U domainEntity);
}
