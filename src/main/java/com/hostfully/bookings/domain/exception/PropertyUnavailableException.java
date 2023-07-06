package com.hostfully.bookings.domain.exception;

public class PropertyUnavailableException extends DomainUseCaseException {
    public PropertyUnavailableException(final String message) {
        super(message);
    }
}
