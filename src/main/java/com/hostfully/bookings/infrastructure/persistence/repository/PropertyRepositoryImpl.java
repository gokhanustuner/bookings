package com.hostfully.bookings.infrastructure.persistence.repository;

import com.hostfully.bookings.domain.entity.Property;
import com.hostfully.bookings.domain.exception.PropertyNotFoundException;
import com.hostfully.bookings.domain.repository.PropertyRepository;
import com.hostfully.bookings.domain.value.PropertyId;
import com.hostfully.bookings.infrastructure.mapper.EntityMapper;
import com.hostfully.bookings.infrastructure.persistence.entity.PropertyEntity;
import com.hostfully.bookings.infrastructure.persistence.jpa.PropertyJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class PropertyRepositoryImpl implements PropertyRepository {

    private final PropertyJPARepository propertyJPARepository;

    private final EntityMapper<PropertyEntity, Property> propertyEntityMapper;

    @Autowired
    public PropertyRepositoryImpl(
            final PropertyJPARepository propertyJPARepository,
            final EntityMapper<PropertyEntity, Property> propertyEntityMapper
    ) {
        this.propertyJPARepository = propertyJPARepository;
        this.propertyEntityMapper = propertyEntityMapper;
    }

    @Override
    public Property findById(final PropertyId propertyId) {
        final PropertyEntity propertyEntity = propertyJPARepository
                .findById(propertyId.value())
                .orElseThrow(
                        () -> new PropertyNotFoundException(String.format("Property with id %s not found", propertyId))
                );

        return propertyEntityMapper.JPAEntityToDomainEntity(propertyEntity);
    }
}
