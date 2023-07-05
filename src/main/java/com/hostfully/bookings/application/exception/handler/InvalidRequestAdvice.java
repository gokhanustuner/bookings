package com.hostfully.bookings.application.exception.handler;

import com.hostfully.bookings.application.response.BadRequestResponse;
import com.hostfully.bookings.domain.exception.DomainException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class InvalidRequestAdvice {
    @ResponseBody
    @ExceptionHandler(DomainException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    BadRequestResponse invalidRequestHandler(final DomainException e) {
        return new BadRequestResponse(e.getMessage(), e.getClass().getSimpleName());
    }
}
