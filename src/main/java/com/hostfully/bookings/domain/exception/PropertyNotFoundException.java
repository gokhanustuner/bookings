package com.hostfully.bookings.domain.exception;

public class PropertyNotFoundException extends DomainEntityNotFoundException {
    public PropertyNotFoundException(final String message) {
        super(message);
    }

    public PropertyNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
