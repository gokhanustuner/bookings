package com.hostfully.bookings.domain.service;

import com.hostfully.bookings.domain.command.booking.CancelBookingCommand;
import com.hostfully.bookings.domain.command.booking.CreateBookingCommand;
import com.hostfully.bookings.domain.command.booking.GetBookingCommand;
import com.hostfully.bookings.domain.command.booking.UpdateBookingCommand;
import com.hostfully.bookings.domain.entity.booking.Booking;

import java.util.List;

public interface BookingService {
    Booking createBooking(CreateBookingCommand createBookingCommand);

    Booking updateBooking(UpdateBookingCommand updateBookingCommand);
    Booking cancelBooking(CancelBookingCommand cancelBookingCommand);
    void reCreateBooking();
    List<Booking> listBookings();

    Booking getBooking(GetBookingCommand getBookingCommand);
}
