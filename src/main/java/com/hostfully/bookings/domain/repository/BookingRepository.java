package com.hostfully.bookings.domain.repository;

import com.hostfully.bookings.domain.entity.Booking;
import com.hostfully.bookings.domain.value.BookingId;

import java.util.Optional;

public interface BookingRepository {
    Booking save(Booking booking);

    Booking findById(BookingId bookingId);
}
