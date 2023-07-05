package com.hostfully.bookings.domain.command.booking;

import com.hostfully.bookings.domain.value.BookingId;

public record GetBookingCommand(BookingId bookingId) {
    public static GetBookingCommand of(final String bookingId) {
        return new GetBookingCommand(BookingId.of(bookingId));
    }
}
