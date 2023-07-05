package com.hostfully.bookings.domain.service;

import com.hostfully.bookings.domain.command.booking.*;
import com.hostfully.bookings.domain.entity.booking.Booking;

import java.util.List;

public interface BookingService {
    Booking createBooking(CreateBookingCommand createBookingCommand);

    Booking updateBooking(UpdateBookingCommand updateBookingCommand);
    Booking cancelBooking(CancelBookingCommand cancelBookingCommand);
    Booking reCreateBooking(ReCreateBookingCommand reCreateBookingCommand);
    List<Booking> listBookings();

    Booking getBooking(GetBookingCommand getBookingCommand);
}
