package com.hostfully.bookings.infrastructure.persistence.repository;

import com.hostfully.bookings.domain.entity.Property;
import com.hostfully.bookings.domain.repository.PropertyRepository;
import com.hostfully.bookings.infrastructure.persistence.jpa.PropertyJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class PropertyRepositoryImpl implements PropertyRepository {

    private final PropertyJPARepository propertyJPARepository;

    @Autowired
    public PropertyRepositoryImpl(final PropertyJPARepository propertyJPARepository) {
        this.propertyJPARepository = propertyJPARepository;
    }

    @Override
    public void save(Property property) {

    }
}
