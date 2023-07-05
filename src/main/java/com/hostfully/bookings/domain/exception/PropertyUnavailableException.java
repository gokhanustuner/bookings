package com.hostfully.bookings.domain.exception;

public class PropertyUnavailableException extends DomainException {
    public PropertyUnavailableException(final String message) {
        super(message);
    }
}
