package com.hostfully.bookings.domain.exception;

abstract public class DomainValueObjectValidationException extends RuntimeException {
    public DomainValueObjectValidationException(final String message) {
        super(message);
    }

    public DomainValueObjectValidationException(final String message, final Throwable reason) {
        super(message, reason);
    }
}
