package com.hostfully.bookings.domain.service;

import com.hostfully.bookings.domain.command.CancelBookingCommand;
import com.hostfully.bookings.domain.command.CreateBookingCommand;
import com.hostfully.bookings.domain.command.GetBookingCommand;
import com.hostfully.bookings.domain.command.UpdateBookingCommand;
import com.hostfully.bookings.domain.entity.Booking;
import com.hostfully.bookings.domain.entity.Property;
import com.hostfully.bookings.domain.repository.BookingRepository;
import com.hostfully.bookings.domain.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public final class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;

    private final PropertyRepository propertyRepository;

    @Autowired
    public BookingServiceImpl(
            final BookingRepository bookingRepository,
            final PropertyRepository propertyRepository
    ) {
        this.bookingRepository = bookingRepository;
        this.propertyRepository = propertyRepository;
    }

    @Override
    public Booking createBooking(final CreateBookingCommand createBookingCommand) {
        final Property property = propertyRepository.findById(createBookingCommand.propertyId());
        final Booking booking = bookingRepository.save(
                Booking.of(createBookingCommand.bookingPeriod(), property)
        );

        return bookingRepository.findById(booking.getBookingId());
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
