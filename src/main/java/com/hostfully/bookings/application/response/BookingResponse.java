package com.hostfully.bookings.application.response;

import com.hostfully.bookings.domain.entity.Booking;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public record BookingResponse(Long id, String startDate, String endDate, PropertyNode property, String status) {
    public static BookingResponse of(
            final Long bookingId,
            final Long propertyId,
            final String propertyName,
            final String startDate,
            final String endDate,
            final String bookingStatus
    ) {
        return new BookingResponse(
                bookingId,
                startDate,
                endDate,
                new PropertyNode(propertyId, propertyName),
                bookingStatus
        );
    }

    public static BookingResponse of(final Booking booking) {
        final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return of(
                booking.getBookingId().value(),
                booking.getProperty().getPropertyId().value(),
                booking.getProperty().getPropertyName().value(),
                dateFormat.format(booking.getBookingPeriod().startDate()),
                dateFormat.format(booking.getBookingPeriod().endDate()),
                booking.getBookingStatus().name()
        );
    }
}
