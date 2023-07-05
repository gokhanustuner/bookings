package com.hostfully.bookings.domain.repository;

import com.hostfully.bookings.domain.entity.Property;

public interface PropertyRepository {
    void save(Property property);
}
