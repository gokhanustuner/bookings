package com.hostfully.bookings.domain.entity.property;

import com.hostfully.bookings.domain.value.PropertyId;
import com.hostfully.bookings.domain.value.PropertyName;

public class Property {
    private final PropertyId propertyId;

    private final PropertyName propertyName;

    private final Boolean blocked;

    private final Boolean unavailable;

    private Property(
            final PropertyId propertyId,
            final PropertyName propertyName,
            final Boolean blocked,
            final Boolean unavailable
    ) {
        this.propertyId = propertyId;
        this.propertyName = propertyName;
        this.blocked = blocked;
        this.unavailable = unavailable;
    }
    public static Property of(final Long propertyId, final String propertyName) {
        return new Property(
                PropertyId.of(propertyId.toString()),
                new PropertyName(propertyName),
                false,
                false
        );
    }

    public static Property of(
            final Long propertyId,
            final String propertyName,
            final Boolean propertyBlocked,
            final Boolean propertyUnavailable
    ) {
        return new Property(
                PropertyId.of(propertyId.toString()),
                new PropertyName(propertyName),
                propertyBlocked,
                propertyUnavailable
        );
    }

    public static Property of(final PropertyId propertyId) {
        return new Property(propertyId, null, false, false);
    }

    public static Property of(final PropertyId propertyId, final PropertyName propertyName) {
        return new Property(propertyId, propertyName, false, false);
    }

    public Boolean blocked() {
        return blocked;
    }

    public Boolean unavailable() {
        return unavailable;
    }

    public PropertyId getPropertyId() {
        return propertyId;
    }

    public PropertyName getPropertyName() {
        return propertyName;
    }
}
