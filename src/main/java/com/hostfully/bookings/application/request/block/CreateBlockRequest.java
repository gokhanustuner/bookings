package com.hostfully.bookings.application.request.block;

public record CreateBlockRequest(String startDate, String endDate, String propertyId, String reasonId) {
}
