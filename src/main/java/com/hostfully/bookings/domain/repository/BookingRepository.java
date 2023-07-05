package com.hostfully.bookings.domain.repository;

import com.hostfully.bookings.domain.entity.booking.Booking;
import com.hostfully.bookings.domain.entity.property.Property;
import com.hostfully.bookings.domain.value.BookingId;
import com.hostfully.bookings.domain.value.BookingPeriod;

import java.util.List;

public interface BookingRepository {
    Booking save(Booking booking);

    Booking findById(BookingId bookingId);

    List<Booking> findAll();

    List<Booking> findActiveBookingsByPropertyAndBookingPeriod(Property property, BookingPeriod bookingPeriod);
}
