package com.hostfully.bookings.domain.exception;

public class BlockReasonNotFoundException extends DomainException {
    public BlockReasonNotFoundException(final String message) {
        super(message);
    }

    public BlockReasonNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
