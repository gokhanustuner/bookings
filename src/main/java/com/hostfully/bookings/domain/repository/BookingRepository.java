package com.hostfully.bookings.domain.repository;

import com.hostfully.bookings.domain.entity.Booking;

public interface BookingRepository {
    void save(Booking booking);
}
