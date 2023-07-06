package com.hostfully.bookings.domain.exception;

public class BlockNotFoundException extends DomainEntityNotFoundException {
    public BlockNotFoundException(final String message) {
        super(message);
    }

    public BlockNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
