package com.hostfully.bookings.domain.value;

import com.hostfully.bookings.domain.exception.InvalidEntityIdException;

public record BookingId(Long value) {
    public static BookingId of(final String bookingId) {
        try {
            if (bookingId == null)
                throw new InvalidEntityIdException("bookingId must be provided");

            if (Long.parseLong(bookingId) < 1)
                throw new InvalidEntityIdException("bookingId must be a positive number");

            return new BookingId(Long.parseLong(bookingId));
        } catch (final NumberFormatException e) {
            throw new InvalidEntityIdException("bookingId must be a numeric entity", e);
        }
    }

    public static BookingId of(final Long bookingId) {
        if (bookingId == null)
            throw new InvalidEntityIdException("bookingId must be provided");

        if (bookingId < 1)
            throw new InvalidEntityIdException("bookingId must be a positive number");

        return new BookingId(bookingId);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
