package com.hostfully.bookings.domain.repository;

import com.hostfully.bookings.domain.entity.Booking;
import com.hostfully.bookings.domain.value.BookingId;

import java.util.List;
import java.util.Optional;

public interface BookingRepository {
    Booking save(Booking booking);

    Booking findById(BookingId bookingId);

    List<Booking> findAll();
}
