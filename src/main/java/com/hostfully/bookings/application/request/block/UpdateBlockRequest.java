package com.hostfully.bookings.application.request.block;

public record UpdateBlockRequest(String startDate, String endDate, String reasonId) {
}
