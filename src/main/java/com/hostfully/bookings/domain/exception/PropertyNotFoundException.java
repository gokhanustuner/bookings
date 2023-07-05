package com.hostfully.bookings.domain.exception;

public class PropertyNotFoundException extends DomainException {
    public PropertyNotFoundException(final String message) {
        super(message);
    }

    public PropertyNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
