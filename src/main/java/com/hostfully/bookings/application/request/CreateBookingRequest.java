package com.hostfully.bookings.application.request;

public record CreateBookingRequest(String startDate, String endDate, String propertyId) {
}
