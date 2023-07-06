package com.hostfully.bookings.domain.exception;

public class BookingNotFoundException extends DomainEntityNotFoundException {
    public BookingNotFoundException(final String message) {
        super(message);
    }

    public BookingNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
