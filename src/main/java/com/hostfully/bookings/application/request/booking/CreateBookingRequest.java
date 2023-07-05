package com.hostfully.bookings.application.request.booking;

public record CreateBookingRequest(String startDate, String endDate, String propertyId) {
}
