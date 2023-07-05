package com.hostfully.bookings.domain.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hostfully.bookings.domain.value.BookingPeriod;
import com.hostfully.bookings.domain.value.BookingId;

@JsonSerialize
public class Booking {

    private BookingId bookingId;

    private BookingPeriod bookingPeriod;

    private Property property;

    public Booking() {
    }
    public Booking(
            final BookingId bookingId,
            final BookingPeriod bookingPeriod,
            final Property property
    ) {
        this.bookingId = bookingId;
        this.bookingPeriod = bookingPeriod;
        this.property = property;
    }
    public static Booking of(
            final BookingId bookingId,
            final BookingPeriod bookingPeriod,
            final Property property
    ) {
        return new Booking(bookingId, bookingPeriod, property);
    }

    public BookingId getBookingId() {
        return bookingId;
    }

    public void setBookingId(BookingId bookingId) {
        this.bookingId = bookingId;
    }

    public BookingPeriod getBookingPeriod() {
        return bookingPeriod;
    }

    public void setBookingPeriod(BookingPeriod bookingPeriod) {
        this.bookingPeriod = bookingPeriod;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }
}
