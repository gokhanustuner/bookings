package com.hostfully.bookings.application.response;

import com.hostfully.bookings.domain.entity.Booking;

import java.util.ArrayList;
import java.util.List;

public record ListBookingsResponse(List<BookingResponse> bookings) {
    public static ListBookingsResponse create(final Booking ... bookings) {
        final List<BookingResponse> bookingNodes = new ArrayList<>();

        for (final Booking booking : bookings)
            bookingNodes.add(BookingResponse.fromBooking(booking));

        return new ListBookingsResponse(bookingNodes);
    }
}
