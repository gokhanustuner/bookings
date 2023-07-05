package com.hostfully.bookings.domain.repository;

import com.hostfully.bookings.domain.entity.Property;
import com.hostfully.bookings.domain.value.PropertyId;

public interface PropertyRepository {
    Property findById(PropertyId propertyId);
}
