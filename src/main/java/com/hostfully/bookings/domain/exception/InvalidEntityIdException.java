package com.hostfully.bookings.domain.exception;

public class InvalidEntityIdException extends DomainValueObjectValidationException {
    public InvalidEntityIdException(final String message) {
        super(message);
    }

    public InvalidEntityIdException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
