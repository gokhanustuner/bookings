package com.hostfully.bookings.infrastructure.mapper;

import org.springframework.stereotype.Component;

@Component
public interface EntityMapper<T, U> {
    U JPAEntityToDomainEntity(T JPAEntity);

    T domainEntityToJPAEntity(U domainEntity);
}
