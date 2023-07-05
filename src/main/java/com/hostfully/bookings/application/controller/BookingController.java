package com.hostfully.bookings.application.controller;

import com.hostfully.bookings.application.request.UpdateBookingRequest;
import com.hostfully.bookings.application.response.*;
import com.hostfully.bookings.domain.command.CancelBookingCommand;
import com.hostfully.bookings.domain.command.CreateBookingCommand;
import com.hostfully.bookings.application.request.CreateBookingRequest;
import com.hostfully.bookings.domain.command.GetBookingCommand;
import com.hostfully.bookings.domain.command.UpdateBookingCommand;
import com.hostfully.bookings.domain.entity.Booking;
import com.hostfully.bookings.domain.entity.Property;
import com.hostfully.bookings.domain.service.BookingService;
import com.hostfully.bookings.domain.value.BookingPeriod;
import com.hostfully.bookings.domain.value.BookingId;
import com.hostfully.bookings.domain.value.PropertyId;
import com.hostfully.bookings.domain.value.PropertyName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
        Booking booking = bookingService.createBooking(
                CreateBookingCommand.of(
                        createBookingRequest.startDate(),
                        createBookingRequest.endDate(),
                        createBookingRequest.propertyId()
                )
        );

        DateFormat dateFormat = new SimpleDateFormat(BookingPeriod.DATE_PATTERN);

        return BookingResponse.of(
                booking.getBookingId().value(),
                booking.getPropertyId().value(),
                booking.getPropertyName().value(),
                dateFormat.format(booking.getBookingPeriod().startDate()),
                dateFormat.format(booking.getBookingPeriod().endDate())
        );
    }

    @GetMapping(value = "/{bookingId}")
    public BookingResponse read(@PathVariable final String bookingId) {
        bookingService.getBooking(GetBookingCommand.of(bookingId));
        return BookingResponse.of(
                Long.parseLong(bookingId),
                85L,
                "Property X",
                "2023-07-20",
                "2023-07-23"
        );
    }

    @GetMapping
    public List<BookingResponse> list() {
        bookingService.listBookings();
        final List<BookingResponse> bookings = new ArrayList<>();
        bookings.add(BookingResponse.fromBooking(Booking.of(
                new BookingId(85L),
                new BookingPeriod(new Date(), new Date()),
                new Property(
                        new PropertyId(3L),
                        new PropertyName("Property X")
                )
        )));

        bookings.add(BookingResponse.fromBooking(Booking.of(
                new BookingId(74L),
                new BookingPeriod(new Date(), new Date()),
                new Property(
                        new PropertyId(4L),
                        new PropertyName("Property Y")
                )
        )));
        return bookings;
    }

    @PutMapping(value = "/{bookingId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public BookingResponse update(
            @RequestBody final UpdateBookingRequest updateBookingRequest,
            @PathVariable final String bookingId
    ) {
        bookingService.updateBooking(
                UpdateBookingCommand.of(
                        updateBookingRequest.startDate(),
                        updateBookingRequest.endDate(),
                        bookingId
                )
        );
        return BookingResponse.of(
                Long.parseLong(bookingId),
                85L,
                "Property X",
                "2023-07-20",
                "2023-07-23"
        );
    }

    @DeleteMapping(value = "/{bookingId}")
    public ResponseEntity<Response> delete(@PathVariable final String bookingId) {
        bookingService.cancelBooking(CancelBookingCommand.of(bookingId));
        return ResponseEntity.accepted().build();
    }
}
