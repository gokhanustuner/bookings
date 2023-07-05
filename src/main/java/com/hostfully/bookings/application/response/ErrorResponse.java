package com.hostfully.bookings.application.response;

import org.springframework.http.HttpStatus;

public record ErrorResponse(String message, String exception) {
}
