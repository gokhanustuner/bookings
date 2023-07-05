package com.hostfully.bookings.infrastructure.mapper;

import com.hostfully.bookings.domain.entity.Property;
import com.hostfully.bookings.infrastructure.persistence.entity.PropertyEntity;
import org.springframework.stereotype.Component;

@Component
public class PropertyEntityMapper implements EntityMapper<PropertyEntity, Property> {
    @Override
    public Property JPAEntityToDomainEntity(final PropertyEntity JPAPropertyEntity) {
        return Property.of(
                JPAPropertyEntity.getId(),
                JPAPropertyEntity.getName()
        );
    }

    @Override
    public PropertyEntity domainEntityToJPAEntity(final Property domainPropertyEntity) {
        final PropertyEntity propertyEntity = new PropertyEntity();

        if (domainPropertyEntity.getPropertyId() != null) {
            propertyEntity.setId(domainPropertyEntity.getPropertyId().value());
        }
        propertyEntity.setName(domainPropertyEntity.getPropertyName().value());

        return propertyEntity;
    }
}
