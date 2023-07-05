package com.hostfully.bookings.domain.service;

import com.hostfully.bookings.domain.command.CancelBookingCommand;
import com.hostfully.bookings.domain.command.CreateBookingCommand;
import com.hostfully.bookings.domain.command.GetBookingCommand;
import com.hostfully.bookings.domain.command.UpdateBookingCommand;
import com.hostfully.bookings.domain.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public final class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;

    @Autowired
    public BookingServiceImpl(final BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Override
    public void createBooking(final CreateBookingCommand createBookingCommand) {

    }

    @Override
    public void updateBooking(UpdateBookingCommand updateBookingCommand) {

    }

    @Override
    public void cancelBooking(final CancelBookingCommand cancelBookingCommand) {

    }

    @Override
    public void reCreateBooking() {

    }

    @Override
    public void listBookings() {

    }

    @Override
    public void getBooking(final GetBookingCommand getBookingCommand) {

    }
}
