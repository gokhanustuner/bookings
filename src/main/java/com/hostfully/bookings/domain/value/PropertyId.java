package com.hostfully.bookings.domain.value;

import com.hostfully.bookings.domain.exception.InvalidEntityIdException;

public record PropertyId(Long value) {
    public static PropertyId of(final String propertyId) {
        try {
            if (Long.parseLong(propertyId) < 1)
                throw new InvalidEntityIdException("propertyId must be a positive number");

            return new PropertyId(Long.parseLong(propertyId));
        } catch (final NumberFormatException e) {
            throw new InvalidEntityIdException("propertyId must be a numeric entity", e);
        }
    }

    public String toString() {
        return value.toString();
    }
}
