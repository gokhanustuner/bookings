package com.hostfully.bookings.domain.exception;

public class InvalidBlockDateException extends DomainValueObjectValidationException {
    public InvalidBlockDateException(final String message) {
        super(message);
    }

    public InvalidBlockDateException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
