package com.hostfully.bookings.infrastructure.persistence.repository;

import com.hostfully.bookings.domain.entity.Booking;
import com.hostfully.bookings.domain.repository.BookingRepository;
import com.hostfully.bookings.infrastructure.persistence.jpa.BookingJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class BookingRepositoryImpl implements BookingRepository {

    private final BookingJPARepository bookingJPARepository;

    @Autowired
    public BookingRepositoryImpl(final BookingJPARepository bookingJPARepository) {
        this.bookingJPARepository = bookingJPARepository;
    }
    @Override
    public void save(Booking booking) {

    }
}
