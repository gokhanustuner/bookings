package com.hostfully.bookings.domain.exception;

abstract public class DomainException extends RuntimeException {
    public DomainException(final String message) {
        super(message);
    }

    public DomainException(final String message, final Throwable reason) {
        super(message, reason);
    }
}
