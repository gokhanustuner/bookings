package com.hostfully.bookings.application.response;

import org.springframework.http.HttpStatus;

public record BadRequestResponse(String message, String exception) {
    public static HttpStatus statusCode = HttpStatus.BAD_REQUEST;
}
