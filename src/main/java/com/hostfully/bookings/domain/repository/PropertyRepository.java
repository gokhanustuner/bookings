package com.hostfully.bookings.domain.repository;

import com.hostfully.bookings.domain.entity.property.Property;
import com.hostfully.bookings.domain.value.PropertyId;

public interface PropertyRepository {
    Property findById(PropertyId propertyId);
}
