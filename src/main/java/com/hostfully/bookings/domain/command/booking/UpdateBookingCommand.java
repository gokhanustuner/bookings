package com.hostfully.bookings.domain.command.booking;

import com.hostfully.bookings.domain.value.BookingId;
import com.hostfully.bookings.domain.value.BookingPeriod;

public record UpdateBookingCommand(BookingId bookingId, BookingPeriod bookingPeriod) {

    public static UpdateBookingCommand of(final String startDate, final String endDate, final String bookingId) {
        return new UpdateBookingCommand(
                BookingId.of(bookingId),
                BookingPeriod.of(startDate, endDate)
        );
    }
}
