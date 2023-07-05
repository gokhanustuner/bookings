package com.hostfully.bookings.domain.command.booking;

import com.hostfully.bookings.domain.entity.booking.BookingStatus;
import com.hostfully.bookings.domain.value.BookingId;

public record CancelBookingCommand(BookingId bookingId, BookingStatus bookingStatus) {
    public static CancelBookingCommand of(final String bookingId) {
        return new CancelBookingCommand(BookingId.of(bookingId), BookingStatus.CANCELLED);
    }
}
