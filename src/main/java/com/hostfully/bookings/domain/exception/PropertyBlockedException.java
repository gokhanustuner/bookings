package com.hostfully.bookings.domain.exception;

public class PropertyBlockedException extends DomainUseCaseException {
    public PropertyBlockedException(final String message) {
        super(message);
    }
}
