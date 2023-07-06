package com.hostfully.bookings.domain.exception;

public class BlockReasonNotFoundException extends DomainEntityNotFoundException {
    public BlockReasonNotFoundException(final String message) {
        super(message);
    }

    public BlockReasonNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
