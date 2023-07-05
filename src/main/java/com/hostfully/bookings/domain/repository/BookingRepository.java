package com.hostfully.bookings.domain.repository;

import com.hostfully.bookings.domain.entity.booking.Booking;
import com.hostfully.bookings.domain.value.BookingId;

import java.util.List;

public interface BookingRepository {
    Booking save(Booking booking);

    Booking findById(BookingId bookingId);

    List<Booking> findAll();
}
