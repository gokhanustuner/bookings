package com.hostfully.bookings.domain.command.booking;

import com.hostfully.bookings.domain.entity.booking.BookingStatus;
import com.hostfully.bookings.domain.value.BookingPeriod;
import com.hostfully.bookings.domain.value.PropertyId;

public record CreateBookingCommand(BookingPeriod bookingPeriod, PropertyId propertyId, BookingStatus bookingStatus) {
    public static CreateBookingCommand of(final String startDate, final String endDate, final String propertyId) {
        return new CreateBookingCommand(
                BookingPeriod.of(startDate, endDate),
                PropertyId.of(propertyId),
                BookingStatus.ACTIVE
        );
    }
}
