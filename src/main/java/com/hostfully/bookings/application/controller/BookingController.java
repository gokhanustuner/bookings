package com.hostfully.bookings.application.controller;

import com.hostfully.bookings.application.request.booking.UpdateBookingRequest;
import com.hostfully.bookings.application.response.*;
import com.hostfully.bookings.domain.command.booking.CancelBookingCommand;
import com.hostfully.bookings.domain.command.booking.CreateBookingCommand;
import com.hostfully.bookings.application.request.booking.CreateBookingRequest;
import com.hostfully.bookings.domain.command.booking.GetBookingCommand;
import com.hostfully.bookings.domain.command.booking.UpdateBookingCommand;
import com.hostfully.bookings.domain.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {
    private final BookingService bookingService;

    @Autowired
    public BookingController(final BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public BookingResponse create(@RequestBody final CreateBookingRequest createBookingRequest) {
        return BookingResponse.of(
                bookingService.createBooking(
                        CreateBookingCommand.of(
                                createBookingRequest.startDate(),
                                createBookingRequest.endDate(),
                                createBookingRequest.propertyId()
                        )
                )
        );
    }

    @GetMapping(value = "/{bookingId}")
    public BookingResponse read(@PathVariable final String bookingId) {
        return BookingResponse.of(
                bookingService.getBooking(GetBookingCommand.of(bookingId))
        );
    }

    @GetMapping
    public List<BookingResponse> list() {
        return bookingService.listBookings().stream().map(BookingResponse::of).toList();
    }

    @PutMapping(value = "/{bookingId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public BookingResponse update(
            @RequestBody final UpdateBookingRequest updateBookingRequest,
            @PathVariable final String bookingId
    ) {
        return BookingResponse.of(
                bookingService.updateBooking(
                        UpdateBookingCommand.of(
                                updateBookingRequest.startDate(),
                                updateBookingRequest.endDate(),
                                bookingId
                        )
                )
        );
    }

    @DeleteMapping(value = "/{bookingId}")
    public BookingResponse delete(@PathVariable final String bookingId) {
        return BookingResponse.of(
                bookingService.cancelBooking(CancelBookingCommand.of(bookingId))
        );
    }
}
