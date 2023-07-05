package com.hostfully.bookings.domain.command;

import com.hostfully.bookings.domain.entity.BookingStatus;
import com.hostfully.bookings.domain.value.BookingPeriod;
import com.hostfully.bookings.domain.value.PropertyId;
import jakarta.validation.constraints.NotNull;

public record CreateBookingCommand(
        @NotNull BookingPeriod bookingPeriod,
        @NotNull PropertyId propertyId,
        @NotNull BookingStatus bookingStatus
) {
    public static CreateBookingCommand of(final String startDate, final String endDate, final String propertyId) {
        return new CreateBookingCommand(
                BookingPeriod.of(startDate, endDate),
                PropertyId.of(propertyId),
                BookingStatus.ACTIVE
        );
    }
}
