package com.hostfully.bookings.domain.entity.booking;

import com.hostfully.bookings.domain.entity.property.Property;
import com.hostfully.bookings.domain.value.BookingPeriod;
import com.hostfully.bookings.domain.value.BookingId;
import com.hostfully.bookings.domain.value.PropertyId;
import com.hostfully.bookings.domain.value.PropertyName;

public class Booking {

    private BookingId bookingId;

    private BookingPeriod bookingPeriod;

    private Property property;

    private BookingStatus bookingStatus;

    private Booking() {
    }
    private Booking(
            final BookingId bookingId,
            final BookingPeriod bookingPeriod,
            final Property property,
            final BookingStatus bookingStatus
    ) {
        this.bookingId = bookingId;
        this.bookingPeriod = bookingPeriod;
        this.property = property;
        this.bookingStatus = bookingStatus;
    }

    public static Booking of(
            final BookingPeriod bookingPeriod,
            final Property property,
            final BookingStatus bookingStatus
    ) {
        return new Booking(null, bookingPeriod, property, bookingStatus);
    }
    public static Booking of(
            final BookingId bookingId,
            final BookingPeriod bookingPeriod,
            final Property property,
            final BookingStatus bookingStatus
    ) {
        return new Booking(bookingId, bookingPeriod, property, bookingStatus);
    }

    public BookingId getBookingId() {
        return bookingId;
    }

    public void setBookingId(final BookingId bookingId) {
        this.bookingId = bookingId;
    }

    public BookingPeriod getBookingPeriod() {
        return bookingPeriod;
    }

    public void setBookingPeriod(final BookingPeriod bookingPeriod) {
        this.bookingPeriod = bookingPeriod;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(final Property property) {
        this.property = property;
    }

    public PropertyId getPropertyId() {
        return property.getPropertyId();
    }

    public PropertyName getPropertyName() {
        return property.getPropertyName();
    }

    public BookingStatus getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(final BookingStatus bookingStatus) {
        this.bookingStatus = bookingStatus;
    }
}
