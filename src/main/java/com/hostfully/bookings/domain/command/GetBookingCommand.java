package com.hostfully.bookings.domain.command;

import com.hostfully.bookings.domain.exception.InvalidEntityIdException;
import com.hostfully.bookings.domain.value.BookingId;
import jakarta.validation.constraints.NotNull;

public record GetBookingCommand(@NotNull BookingId bookingId) {
    public static GetBookingCommand of(final String bookingId) {
        try {
            if (Long.parseLong(bookingId) < 1)
                throw new InvalidEntityIdException("bookingId must be a positive number");

            return new GetBookingCommand(new BookingId(Long.parseLong(bookingId)));
        } catch (final NumberFormatException e) {
            throw new InvalidEntityIdException("bookingId must be a numeric entity", e);
        }
    }
}
