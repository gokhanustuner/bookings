package com.hostfully.bookings.domain.service;

import com.hostfully.bookings.domain.command.CancelBookingCommand;
import com.hostfully.bookings.domain.command.CreateBookingCommand;
import com.hostfully.bookings.domain.command.GetBookingCommand;
import com.hostfully.bookings.domain.command.UpdateBookingCommand;
import com.hostfully.bookings.domain.entity.Booking;

import java.util.List;

public interface BookingService {
    Booking createBooking(CreateBookingCommand createBookingCommand);

    Booking updateBooking(UpdateBookingCommand updateBookingCommand);
    Booking cancelBooking(CancelBookingCommand cancelBookingCommand);
    void reCreateBooking();
    List<Booking> listBookings();

    Booking getBooking(GetBookingCommand getBookingCommand);
}
