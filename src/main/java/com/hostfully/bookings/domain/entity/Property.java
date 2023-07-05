package com.hostfully.bookings.domain.entity;

import com.hostfully.bookings.domain.value.PropertyId;
import com.hostfully.bookings.domain.value.PropertyName;

public record Property(PropertyId propertyId, PropertyName propertyName) {
}
