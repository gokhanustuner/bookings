package com.hostfully.bookings.domain.service;

import com.hostfully.bookings.domain.command.booking.CancelBookingCommand;
import com.hostfully.bookings.domain.command.booking.CreateBookingCommand;
import com.hostfully.bookings.domain.command.booking.GetBookingCommand;
import com.hostfully.bookings.domain.command.booking.UpdateBookingCommand;
import com.hostfully.bookings.domain.entity.booking.Booking;
import com.hostfully.bookings.domain.entity.property.Property;
import com.hostfully.bookings.domain.repository.BookingRepository;
import com.hostfully.bookings.domain.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public final class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;

    private final PropertyRepository propertyRepository;

    @Autowired
    public BookingServiceImpl(final BookingRepository bookingRepository, final PropertyRepository propertyRepository) {
        this.bookingRepository = bookingRepository;
        this.propertyRepository = propertyRepository;
    }

    @Override
    public Booking createBooking(final CreateBookingCommand createBookingCommand) {
        final Property property = propertyRepository.findById(createBookingCommand.propertyId());
        final Booking booking = bookingRepository.save(
                Booking.of(
                        createBookingCommand.bookingPeriod(),
                        property,
                        createBookingCommand.bookingStatus()
                )
        );

        return bookingRepository.findById(booking.getBookingId());
    }

    @Override
    public Booking updateBooking(final UpdateBookingCommand updateBookingCommand) {
        final Booking booking = bookingRepository.findById(updateBookingCommand.bookingId());

        booking.setBookingPeriod(updateBookingCommand.bookingPeriod());
        bookingRepository.save(booking);

        return booking;
    }

    @Override
    public Booking cancelBooking(final CancelBookingCommand cancelBookingCommand) {
        final Booking booking = bookingRepository.findById(cancelBookingCommand.bookingId());

        booking.setBookingStatus(cancelBookingCommand.bookingStatus());
        bookingRepository.save(booking);

        return booking;
    }

    @Override
    public void reCreateBooking() {

    }

    @Override
    public List<Booking> listBookings() {
        return bookingRepository.findAll();
    }

    @Override
    public Booking getBooking(final GetBookingCommand getBookingCommand) {
        return bookingRepository.findById(getBookingCommand.bookingId());
    }
}
