package com.hostfully.bookings.domain.command;

import com.hostfully.bookings.domain.value.BookingId;
import com.hostfully.bookings.domain.value.BookingPeriod;
import jakarta.validation.constraints.NotNull;

public record UpdateBookingCommand(@NotNull BookingId bookingId, @NotNull BookingPeriod bookingPeriod) {

    public static UpdateBookingCommand of(final String startDate, final String endDate, final String bookingId) {
        return new UpdateBookingCommand(
                BookingId.of(bookingId),
                BookingPeriod.of(startDate, endDate)
        );
    }
}
