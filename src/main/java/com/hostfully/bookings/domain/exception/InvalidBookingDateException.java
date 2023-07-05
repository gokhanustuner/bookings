package com.hostfully.bookings.domain.exception;

public class InvalidBookingDateException extends DomainException {
    public InvalidBookingDateException(final String message) {
        super(message);
    }

    public InvalidBookingDateException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
