package com.hostfully.bookings.domain.service;

import com.hostfully.bookings.domain.command.booking.*;
import com.hostfully.bookings.domain.entity.block.Block;
import com.hostfully.bookings.domain.entity.booking.Booking;
import com.hostfully.bookings.domain.entity.property.Property;
import com.hostfully.bookings.domain.exception.PropertyBlockedException;
import com.hostfully.bookings.domain.exception.PropertyUnavailableException;
import com.hostfully.bookings.domain.repository.BlockRepository;
import com.hostfully.bookings.domain.repository.BookingRepository;
import com.hostfully.bookings.domain.repository.PropertyRepository;
import com.hostfully.bookings.domain.value.BlockPeriod;
import com.hostfully.bookings.domain.value.BookingId;
import com.hostfully.bookings.domain.value.BookingPeriod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public final class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;

    private final PropertyRepository propertyRepository;

    private final BlockRepository blockRepository;

    @Autowired
    public BookingServiceImpl(
            final BookingRepository bookingRepository,
            final PropertyRepository propertyRepository,
            final BlockRepository blockRepository
    ) {
        this.bookingRepository = bookingRepository;
        this.propertyRepository = propertyRepository;
        this.blockRepository = blockRepository;
    }

    @Override
    public Booking createBooking(final CreateBookingCommand createBookingCommand) {
        final Property property = propertyRepository.findById(createBookingCommand.propertyId());

        checkPropertyBlockedInBookingPeriod(property, createBookingCommand.bookingPeriod());
        checkBookingsOverlapInBookingPeriod(property, createBookingCommand.bookingPeriod(), null);

        final Booking booking = bookingRepository.save(
                Booking.of(
                        createBookingCommand.bookingPeriod(),
                        property,
                        createBookingCommand.bookingStatus()
                )
        );

        booking.setProperty(property);

        return booking;
    }

    @Override
    public Booking updateBooking(final UpdateBookingCommand updateBookingCommand) {
        final Booking booking = bookingRepository.findActiveBookingById(updateBookingCommand.bookingId());

        checkPropertyBlockedInBookingPeriod(booking.getProperty(), updateBookingCommand.bookingPeriod());
        checkBookingsOverlapInBookingPeriod(booking.getProperty(), updateBookingCommand.bookingPeriod(), updateBookingCommand.bookingId());

        booking.setBookingPeriod(updateBookingCommand.bookingPeriod());
        bookingRepository.save(booking);

        return booking;
    }

    @Override
    public Booking cancelBooking(final CancelBookingCommand cancelBookingCommand) {
        final Booking booking = bookingRepository.findActiveBookingById(cancelBookingCommand.bookingId());

        booking.setBookingStatus(cancelBookingCommand.bookingStatus());
        bookingRepository.save(booking);

        return booking;
    }

    @Override
    public Booking reCreateBooking(final ReCreateBookingCommand reCreateBookingCommand) {
        final Booking booking = bookingRepository.findCancelledBookingById(reCreateBookingCommand.bookingId());

        return createBooking(
                CreateBookingCommand.of(
                        booking.getBookingPeriod(),
                        booking.getPropertyId()
                )
        );
    }

    @Override
    public List<Booking> listBookings() {
        return bookingRepository.findAll();
    }

    @Override
    public Booking getBooking(final GetBookingCommand getBookingCommand) {
        return bookingRepository.findById(getBookingCommand.bookingId());
    }

    private void checkBookingsOverlapInBookingPeriod(
            final Property property,
            final BookingPeriod bookingPeriod,
            final BookingId excludedBookingId
    ) {
        final List<Booking> bookings = bookingRepository.findActiveBookingsByPropertyAndBookingPeriod(
                property,
                bookingPeriod
        );

        if (excludedBookingId != null) {
            if (bookings.size() == 1 && !bookings.get(0).getBookingId().equals(excludedBookingId)) {
                throw new PropertyUnavailableException(
                        String.format(
                                "Property with id %s is unavailable during the time you requested for booking.",
                                property.getPropertyId()
                        )
                );
            } else if (bookings.size() > 1) {
                throw new PropertyUnavailableException(
                        String.format(
                                "Property with id %s is unavailable during the time you requested for booking.",
                                property.getPropertyId()
                        )
                );
            }
        } else {
            if (bookings.size() > 0)
                throw new PropertyUnavailableException(
                        String.format(
                                "Property with id %s is unavailable during the time you requested for booking.",
                                property.getPropertyId()
                        )
                );
        }
    }

    private void checkPropertyBlockedInBookingPeriod(final Property property, final BookingPeriod bookingPeriod) {
        final List<Block> blocks = blockRepository.findActiveBlocksByPropertyAndBlockPeriod(
                property,
                BlockPeriod.of(
                        bookingPeriod.startDate(),
                        bookingPeriod.endDate()
                )
        );

        if (blocks.size() > 0)
            throw new PropertyBlockedException(
                    String.format(
                            "Property with id %s is blocked during the time you requested for booking.",
                            property.getPropertyId()
                    )
            );
    }
}
