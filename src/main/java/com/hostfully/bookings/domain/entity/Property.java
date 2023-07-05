package com.hostfully.bookings.domain.entity;

import com.hostfully.bookings.domain.value.PropertyId;
import com.hostfully.bookings.domain.value.PropertyName;

public class Property {
    private final PropertyId propertyId;

    private final PropertyName propertyName;

    public Property(final PropertyId propertyId, final PropertyName propertyName) {
        this.propertyId = propertyId;
        this.propertyName = propertyName;
    }
    public static Property of(final Long propertyId, final String propertyName) {
        return new Property(
                PropertyId.of(propertyId.toString()),
                new PropertyName(propertyName)
        );
    }

    public static Property of(final PropertyId propertyId) {
        return new Property(propertyId, null);
    }

    public static Property of(final PropertyId propertyId, final PropertyName propertyName) {
        return new Property(propertyId, propertyName);
    }

    public PropertyId getPropertyId() {
        return propertyId;
    }

    public PropertyName getPropertyName() {
        return propertyName;
    }
}
