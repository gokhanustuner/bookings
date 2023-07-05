package com.hostfully.bookings.domain.service;

import com.hostfully.bookings.domain.command.CancelBookingCommand;
import com.hostfully.bookings.domain.command.CreateBookingCommand;
import com.hostfully.bookings.domain.command.GetBookingCommand;
import com.hostfully.bookings.domain.command.UpdateBookingCommand;

public interface BookingService {
    void createBooking(CreateBookingCommand createBookingCommand);

    void updateBooking(UpdateBookingCommand updateBookingCommand);
    void cancelBooking(CancelBookingCommand cancelBookingCommand);
    void reCreateBooking();
    void listBookings();

    void getBooking(GetBookingCommand getBookingCommand);
}
