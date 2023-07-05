package com.hostfully.bookings.application.response;

import com.hostfully.bookings.domain.entity.Property;
import com.hostfully.bookings.domain.value.BookingPeriod;
import com.hostfully.bookings.domain.value.BookingId;

public class ReadBookingResponse extends Response {
    private BookingId bookingId;

    private BookingPeriod bookingPeriod;

    private Property property;

    private ReadBookingResponse(
            final BookingId bookingId,
            final BookingPeriod bookingPeriod,
            final Property property
    ) {
        this.bookingId = bookingId;
        this.bookingPeriod = bookingPeriod;
        this.property = property;
    }

    public Long getBookingId() {
        return bookingId.value();
    }

    public void setBookingId(BookingId bookingId) {
        this.bookingId = bookingId;
    }

    public BookingPeriod getBookingDate() {
        return bookingPeriod;
    }

    public void setBookingDate(BookingPeriod bookingPeriod) {
        this.bookingPeriod = bookingPeriod;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public static ReadBookingResponse create(
            final BookingId bookingId,
            final BookingPeriod bookingPeriod,
            final Property property
    ) {
        return new ReadBookingResponse(bookingId, bookingPeriod, property);
    }
}
