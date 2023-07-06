package com.hostfully.bookings.domain.exception;

abstract public class DomainEntityNotFoundException extends RuntimeException {
    public DomainEntityNotFoundException(final String message) {
        super(message);
    }

    public DomainEntityNotFoundException(final String message, final Throwable reason) {
        super(message, reason);
    }
}
