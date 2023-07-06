package com.hostfully.bookings.application.exception.handler;

import com.hostfully.bookings.application.response.ErrorResponse;
import com.hostfully.bookings.domain.exception.DomainEntityNotFoundException;
import com.hostfully.bookings.domain.exception.DomainException;
import com.hostfully.bookings.domain.exception.DomainUseCaseException;
import com.hostfully.bookings.domain.exception.DomainValueObjectValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class BookingsApplicationExceptionHandler {
    @ResponseBody
    @ExceptionHandler(DomainValueObjectValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorResponse badRequestHandler(final DomainValueObjectValidationException e) {
        return new ErrorResponse(e.getMessage(), e.getClass().getSimpleName());
    }

    @ResponseBody
    @ExceptionHandler(DomainEntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ErrorResponse notFoundHandler(final DomainEntityNotFoundException e) {
        return new ErrorResponse(e.getMessage(), e.getClass().getSimpleName());
    }

    @ResponseBody
    @ExceptionHandler(DomainUseCaseException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    ErrorResponse conflictHandler(final DomainUseCaseException e) {
        return new ErrorResponse(e.getMessage(), e.getClass().getSimpleName());
    }
}
