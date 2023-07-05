package com.hostfully.bookings.domain.command.booking;

import com.hostfully.bookings.domain.value.BookingId;

public record ReCreateBookingCommand(BookingId bookingId) {
    public static ReCreateBookingCommand of(final String bookingId) {
        return new ReCreateBookingCommand(BookingId.of(bookingId));
    }
}
