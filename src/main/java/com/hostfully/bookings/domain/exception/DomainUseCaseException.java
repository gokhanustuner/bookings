package com.hostfully.bookings.domain.exception;

abstract public class DomainUseCaseException extends RuntimeException {
    public DomainUseCaseException(final String message) {
        super(message);
    }

    public DomainUseCaseException(final String message, final Throwable reason) {
        super(message, reason);
    }
}
