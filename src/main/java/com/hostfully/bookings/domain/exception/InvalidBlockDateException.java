package com.hostfully.bookings.domain.exception;

public class InvalidBlockDateException extends DomainException {
    public InvalidBlockDateException(final String message) {
        super(message);
    }

    public InvalidBlockDateException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
