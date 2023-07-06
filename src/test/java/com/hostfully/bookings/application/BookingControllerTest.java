package com.hostfully.bookings.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hostfully.bookings.application.response.BookingResponse;
import com.hostfully.bookings.domain.entity.booking.BookingStatus;
import com.hostfully.bookings.domain.exception.*;
import com.hostfully.bookings.domain.value.BookingPeriod;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BookingControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void givenInvalidStartDateThenCreateBookingReturns400() throws Exception {
        final String endDate = LocalDate.now().plusDays(2).format(DateTimeFormatter.ofPattern(BookingPeriod.DATE_PATTERN));
        mvc.perform(
                        post("/bookings")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .accept(MediaType.APPLICATION_JSON_VALUE)
                                .content(
                                        "{\n" +
                                                "    \"startDate\": \"2023-08-248\",\n" +
                                                "    \"endDate\": \"" + endDate + "\",\n" +
                                                "    \"propertyId\": 1\n" +
                                                "}"
                                )
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.exception").exists())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.exception").value(InvalidBookingDateException.class.getSimpleName()))
                .andExpect(jsonPath("$.message").value("startDate must be a valid date"))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof InvalidBookingDateException))
                .andExpect(
                        result -> assertEquals(
                                "startDate must be a valid date", result.getResolvedException().getMessage()
                        )
                );
    }

    @Test
    public void givenInvalidEndDateThenCreateBookingReturns400() throws Exception {
        final String startDate = LocalDate.now().plusDays(2).format(DateTimeFormatter.ofPattern(BookingPeriod.DATE_PATTERN));
        mvc.perform(
                        post("/bookings")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .accept(MediaType.APPLICATION_JSON_VALUE)
                                .content(
                                        "{\n" +
                                                "    \"startDate\": \"" + startDate + "\",\n" +
                                                "    \"endDate\": \"2023-087-28\",\n" +
                                                "    \"propertyId\": 1\n" +
                                                "}"
                                )
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.exception").exists())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.exception").value(InvalidBookingDateException.class.getSimpleName()))
                .andExpect(jsonPath("$.message").value("endDate must be a valid date"))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof InvalidBookingDateException))
                .andExpect(result ->
                        assertEquals("endDate must be a valid date", result.getResolvedException().getMessage())
                );
    }

    @Test
    public void givenStartDateIsTodaysDateThenCreateBookingReturns400() throws Exception {
        mvc.perform(
                        post("/bookings")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .accept(MediaType.APPLICATION_JSON_VALUE)
                                .content(
                                        "{\n" +
                                                "    \"startDate\": \"" +
                                                LocalDate.now().format(DateTimeFormatter.ofPattern(BookingPeriod.DATE_PATTERN))
                                                + "\",\n" +
                                                "    \"endDate\": \"" + LocalDate.now().plusDays(2).format(DateTimeFormatter.ofPattern(BookingPeriod.DATE_PATTERN)) + "\",\n" +
                                                "    \"propertyId\": 1\n" +
                                                "}"
                                )
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.exception").exists())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.exception").value(InvalidBookingDateException.class.getSimpleName()))
                .andExpect(jsonPath("$.message").value("startDate must be a future date"))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof InvalidBookingDateException))
                .andExpect(result ->
                        assertEquals("startDate must be a future date", result.getResolvedException().getMessage())
                );
    }

    @Test
    public void givenEndDateIsTodaysDateThenCreateBookingReturns400() throws Exception {
        mvc.perform(
                        post("/bookings")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .accept(MediaType.APPLICATION_JSON_VALUE)
                                .content("{\n" +
                                        "    \"startDate\": \"" + LocalDate.now().plusDays(2).format(DateTimeFormatter.ofPattern(BookingPeriod.DATE_PATTERN)) + "\",\n" +
                                        "    \"endDate\": \"" + LocalDate.now().format(DateTimeFormatter.ofPattern(BookingPeriod.DATE_PATTERN)) + "\",\n" +
                                        "    \"propertyId\": 1\n" +
                                        "}"
                                )
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.exception").exists())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.exception").value(InvalidBookingDateException.class.getSimpleName()))
                .andExpect(jsonPath("$.message").value("endDate must be a future date"))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof InvalidBookingDateException))
                .andExpect(result ->
                        assertEquals("endDate must be a future date", result.getResolvedException().getMessage())
                );
    }

    @Test
    public void givenEndDateIsSmallerThanStartDateThenCreateBookingReturns400() throws Exception {
        final String startDate = LocalDate.now().plusDays(5).format(DateTimeFormatter.ofPattern(BookingPeriod.DATE_PATTERN));
        final String endDate = LocalDate.now().plusDays(4).format(DateTimeFormatter.ofPattern(BookingPeriod.DATE_PATTERN));
        mvc.perform(
                        post("/bookings")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .accept(MediaType.APPLICATION_JSON_VALUE)
                                .content(
                                        "{\n" +
                                                "    \"startDate\": \"" + startDate + "\",\n" +
                                                "    \"endDate\": \"" + endDate + "\",\n" +
                                                "    \"propertyId\": 1\n" +
                                                "}"
                                )
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.exception").exists())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.exception").value(InvalidBookingDateException.class.getSimpleName()))
                .andExpect(jsonPath("$.message").value("endDate must be greater than the startDate"))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof InvalidBookingDateException))
                .andExpect(result ->
                        assertEquals("endDate must be greater than the startDate", result.getResolvedException().getMessage())
                );
    }

    @Test
    public void givenPropertyIdIsStringThenCreateBookingReturns200() throws Exception {
        final String startDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern(BookingPeriod.DATE_PATTERN));
        final String endDate = LocalDate.now().plusDays(5).format(DateTimeFormatter.ofPattern(BookingPeriod.DATE_PATTERN));
        mvc.perform(
                        post("/bookings")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .accept(MediaType.APPLICATION_JSON_VALUE)
                                .content(
                                        "{\n" +
                                                "    \"startDate\": \"" + startDate + "\",\n" +
                                                "    \"endDate\": \"" + endDate + "\",\n" +
                                                "    \"propertyId\": \"1\"\n" +
                                                "}"
                                )
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.exception").doesNotExist())
                .andExpect(jsonPath("$.message").doesNotExist())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.startDate").exists())
                .andExpect(jsonPath("$.endDate").exists())
                .andExpect(jsonPath("$.startDate").value(startDate))
                .andExpect(jsonPath("$.endDate").value(endDate))
                .andExpect(jsonPath("$.property.id").exists())
                .andExpect(jsonPath("$.property.id").value(1))
                .andExpect(jsonPath("$.property.name").exists())
                .andExpect(jsonPath("$.status").exists())
                .andExpect(jsonPath("$.status").value(BookingStatus.ACTIVE.name()));
    }

    @Test
    public void givenPropertyIdIsNumericThenCreateBookingReturns200() throws Exception {
        final String startDate = LocalDate.now().plusDays(7).format(DateTimeFormatter.ofPattern(BookingPeriod.DATE_PATTERN));
        final String endDate = LocalDate.now().plusDays(9).format(DateTimeFormatter.ofPattern(BookingPeriod.DATE_PATTERN));
        mvc.perform(
                        post("/bookings")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .accept(MediaType.APPLICATION_JSON_VALUE)
                                .content(
                                        "{\n" +
                                                "    \"startDate\": \"" + startDate + "\",\n" +
                                                "    \"endDate\": \"" + endDate + "\",\n" +
                                                "    \"propertyId\": 8\n" +
                                                "}"
                                )
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.exception").doesNotExist())
                .andExpect(jsonPath("$.message").doesNotExist())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.startDate").exists())
                .andExpect(jsonPath("$.endDate").exists())
                .andExpect(jsonPath("$.startDate").value(startDate))
                .andExpect(jsonPath("$.endDate").value(endDate))
                .andExpect(jsonPath("$.property.id").exists())
                .andExpect(jsonPath("$.property.id").value(8))
                .andExpect(jsonPath("$.property.name").exists())
                .andExpect(jsonPath("$.status").exists())
                .andExpect(jsonPath("$.status").value(BookingStatus.ACTIVE.name()));
    }

    @Test
    public void givenPropertyIdIsNonNumericStringThenCreateBookingReturns400() throws Exception {
        final String startDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern(BookingPeriod.DATE_PATTERN));
        final String endDate = LocalDate.now().plusDays(5).format(DateTimeFormatter.ofPattern(BookingPeriod.DATE_PATTERN));
        mvc.perform(
                        post("/bookings")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .accept(MediaType.APPLICATION_JSON_VALUE)
                                .content(
                                        "{\n" +
                                                "    \"startDate\": \"" + startDate + "\",\n" +
                                                "    \"endDate\": \"" + endDate + "\",\n" +
                                                "    \"propertyId\": \"*1\"\n" +
                                                "}"
                                )
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.exception").exists())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.exception").value(InvalidEntityIdException.class.getSimpleName()))
                .andExpect(jsonPath("$.message").value("propertyId must be a numeric entity"))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof InvalidEntityIdException))
                .andExpect(result ->
                        assertEquals("propertyId must be a numeric entity", result.getResolvedException().getMessage())
                );
    }

    @Test
    public void givenPropertyIdIsNegativeNumericStringThenCreateBookingReturns400() throws Exception {
        final String startDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern(BookingPeriod.DATE_PATTERN));
        final String endDate = LocalDate.now().plusDays(5).format(DateTimeFormatter.ofPattern(BookingPeriod.DATE_PATTERN));
        mvc.perform(
                        post("/bookings")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .accept(MediaType.APPLICATION_JSON_VALUE)
                                .content(
                                        "{\n" +
                                                "    \"startDate\": \"" + startDate + "\",\n" +
                                                "    \"endDate\": \"" + endDate + "\",\n" +
                                                "    \"propertyId\": \"-1\"\n" +
                                                "}"
                                )
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.exception").exists())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.exception").value(InvalidEntityIdException.class.getSimpleName()))
                .andExpect(jsonPath("$.message").value("propertyId must be a positive number"))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof InvalidEntityIdException))
                .andExpect(result ->
                        assertEquals("propertyId must be a positive number", result.getResolvedException().getMessage())
                );
    }

    @Test
    public void givenPropertyIdIsZeroThenCreateBookingReturns400() throws Exception {
        final String startDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern(BookingPeriod.DATE_PATTERN));
        final String endDate = LocalDate.now().plusDays(5).format(DateTimeFormatter.ofPattern(BookingPeriod.DATE_PATTERN));
        mvc.perform(
                        post("/bookings")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .accept(MediaType.APPLICATION_JSON_VALUE)
                                .content(
                                        "{\n" +
                                                "    \"startDate\": \"" + startDate + "\",\n" +
                                                "    \"endDate\": \"" + endDate + "\",\n" +
                                                "    \"propertyId\": 0\n" +
                                                "}"
                                )
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.exception").exists())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.exception").value(InvalidEntityIdException.class.getSimpleName()))
                .andExpect(jsonPath("$.message").value("propertyId must be a positive number"))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof InvalidEntityIdException))
                .andExpect(result ->
                        assertEquals("propertyId must be a positive number", result.getResolvedException().getMessage())
                );
    }

    @Test
    public void givenPropertyIsUnavailableThenCreateBookingReturns409() throws Exception {
        final String startDate = LocalDate.now().plusDays(9).format(DateTimeFormatter.ofPattern(BookingPeriod.DATE_PATTERN));
        final String endDate = LocalDate.now().plusDays(11).format(DateTimeFormatter.ofPattern(BookingPeriod.DATE_PATTERN));

        mvc.perform(
                post("/bookings")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(
                                "{\n" +
                                        "    \"startDate\": \"" + startDate + "\",\n" +
                                        "    \"endDate\": \"" + endDate + "\",\n" +
                                        "    \"propertyId\": 1\n" +
                                        "}"
                        )
        );
        mvc.perform(
                        post("/bookings")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .accept(MediaType.APPLICATION_JSON_VALUE)
                                .content(
                                        "{\n" +
                                                "    \"startDate\": \"" + startDate + "\",\n" +
                                                "    \"endDate\": \"" + endDate + "\",\n" +
                                                "    \"propertyId\": 1\n" +
                                                "}"
                                )
                )
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.exception").exists())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.exception").value(PropertyUnavailableException.class.getSimpleName()))
                .andExpect(jsonPath("$.message").value(
                        "Property with id 1 is unavailable during the time you requested for booking."
                ))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof PropertyUnavailableException))
                .andExpect(result ->
                        assertEquals(
                                "Property with id 1 is unavailable during the time you requested for booking.",
                                result.getResolvedException().getMessage()
                        )
                );
    }

    @Test
    public void givenPropertyIsBlockedThenCreateBookingReturns409() throws Exception {
        final String blockStartDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern(BookingPeriod.DATE_PATTERN));
        final String blockEndDate = LocalDate.now().plusDays(8).format(DateTimeFormatter.ofPattern(BookingPeriod.DATE_PATTERN));
        final String bookingStartDate = LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern(BookingPeriod.DATE_PATTERN));
        final String bookingEndDate = LocalDate.now().plusDays(4).format(DateTimeFormatter.ofPattern(BookingPeriod.DATE_PATTERN));

        mvc.perform(
                post("/blocks")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(
                                "{\n" +
                                        "    \"startDate\": \"" + blockStartDate + "\",\n" +
                                        "    \"endDate\": \"" + blockEndDate + "\",\n" +
                                        "    \"propertyId\": 2,\n" +
                                        "    \"reasonId\": 1\n" +
                                        "}"
                        )
        );
        mvc.perform(
                        post("/bookings")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .accept(MediaType.APPLICATION_JSON_VALUE)
                                .content(
                                        "{\n" +
                                                "    \"startDate\": \"" + bookingStartDate + "\",\n" +
                                                "    \"endDate\": \"" + bookingEndDate + "\",\n" +
                                                "    \"propertyId\": 2\n" +
                                                "}"
                                )
                )
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.exception").exists())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.exception").value(PropertyBlockedException.class.getSimpleName()))
                .andExpect(jsonPath("$.message").value(
                        "Property with id 2 is blocked during the time you requested for booking."
                ))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof PropertyBlockedException))
                .andExpect(result ->
                        assertEquals(
                                "Property with id 2 is blocked during the time you requested for booking.",
                                result.getResolvedException().getMessage()
                        )
                );
    }

    @Test
    public void givenBookingExistsThenReadBookingReturns200() throws Exception {
        final String startDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern(BookingPeriod.DATE_PATTERN));
        final String endDate = LocalDate.now().plusDays(5).format(DateTimeFormatter.ofPattern(BookingPeriod.DATE_PATTERN));
        final String bookingResponseContent = mvc.perform(
                post("/bookings")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(
                                "{\n" +
                                        "    \"startDate\": \"" + startDate + "\",\n" +
                                        "    \"endDate\": \"" + endDate + "\",\n" +
                                        "    \"propertyId\": 3\n" +
                                        "}"
                        )
        ).andReturn().getResponse().getContentAsString();

        final BookingResponse bookingResponse = objectMapper.readValue(bookingResponseContent, BookingResponse.class);

        mvc.perform(
                        get(String.format("/bookings/%s", bookingResponse.id())).accept(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.exception").doesNotExist())
                .andExpect(jsonPath("$.message").doesNotExist())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.id").value(bookingResponse.id()))
                .andExpect(jsonPath("$.startDate").exists())
                .andExpect(jsonPath("$.startDate").value(startDate))
                .andExpect(jsonPath("$.endDate").exists())
                .andExpect(jsonPath("$.endDate").value(endDate))
                .andExpect(jsonPath("$.property.id").exists())
                .andExpect(jsonPath("$.property.id").value(3))
                .andExpect(jsonPath("$.property.name").exists())
                .andExpect(jsonPath("$.status").exists())
                .andExpect(jsonPath("$.status").value(BookingStatus.ACTIVE.name()));
    }

    @Test
    public void givenBookingDoesNotExistThenReadBookingReturns404() throws Exception {

        mvc.perform(
                        get("/bookings/550").accept(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.exception").exists())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.exception").value(BookingNotFoundException.class.getSimpleName()))
                .andExpect(jsonPath("$.message").value(
                        "Booking with id 550 not found"
                ))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BookingNotFoundException))
                .andExpect(result ->
                        assertEquals(
                                "Booking with id 550 not found",
                                result.getResolvedException().getMessage()
                        )
                );
    }

    @Test
    public void givenNonExistentPropertyForBookingThenCreateBookingReturns404() throws Exception {
        final String startDate = LocalDate.now().plusDays(33).format(DateTimeFormatter.ofPattern(BookingPeriod.DATE_PATTERN));
        final String endDate = LocalDate.now().plusDays(45).format(DateTimeFormatter.ofPattern(BookingPeriod.DATE_PATTERN));
        mvc.perform(
                post("/bookings")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(
                            "{\n" +
                            "    \"startDate\": \"" + startDate + "\",\n" +
                            "    \"endDate\": \"" + endDate + "\",\n" +
                            "    \"propertyId\": 75\n" +
                            "}"
                        )
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.exception").exists())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.exception").value(PropertyNotFoundException.class.getSimpleName()))
                .andExpect(jsonPath("$.message").value(
                        String.format("Property with id %s not found", 75)
                ))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof PropertyNotFoundException))
                .andExpect(result ->
                        assertEquals(
                                String.format("Property with id %s not found", 75),
                                result.getResolvedException().getMessage()
                        )
                );
    }

    @Test
    public void givenBookingsExistThenListBookingsReturnsAllWithStatusCode200() throws Exception {
        final String startDate = LocalDate.now().plusDays(33).format(DateTimeFormatter.ofPattern(BookingPeriod.DATE_PATTERN));
        final String endDate = LocalDate.now().plusDays(45).format(DateTimeFormatter.ofPattern(BookingPeriod.DATE_PATTERN));
        mvc.perform(
                post("/bookings")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(
                                "{\n" +
                                        "    \"startDate\": \"" + startDate + "\",\n" +
                                        "    \"endDate\": \"" + endDate + "\",\n" +
                                        "    \"propertyId\": 4\n" +
                                        "}"
                        )
        );

        mvc.perform(
                        get("/bookings").accept(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.exception").doesNotExist())
                .andExpect(jsonPath("$.message").doesNotExist())
                .andExpect(jsonPath("$.*.id").exists())
                .andExpect(jsonPath("$.*.startDate").exists())
                .andExpect(jsonPath("$.*.endDate").exists())
                .andExpect(jsonPath("$.*.property.id").exists())
                .andExpect(jsonPath("$.*.property.name").exists())
                .andExpect(jsonPath("$.*.status").exists());
    }

    @Test
    public void givenInvalidStartDateThenUpdateBookingReturns400() throws Exception {
        final String endDate = LocalDate.now().plusDays(2).format(DateTimeFormatter.ofPattern(BookingPeriod.DATE_PATTERN));
        mvc.perform(
                        put("/bookings/1")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .accept(MediaType.APPLICATION_JSON_VALUE)
                                .content(
                                        "{\n" +
                                                "    \"startDate\": \"2023-08-248\",\n" +
                                                "    \"endDate\": \"" + endDate + "\"" +
                                                "}"
                                )
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.exception").exists())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.exception").value(InvalidBookingDateException.class.getSimpleName()))
                .andExpect(jsonPath("$.message").value("startDate must be a valid date"))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof InvalidBookingDateException))
                .andExpect(
                        result -> assertEquals(
                                "startDate must be a valid date", result.getResolvedException().getMessage()
                        )
                );
    }

    @Test
    public void givenInvalidEndDateThenUpdateBookingReturns400() throws Exception {
        final String startDate = LocalDate.now().plusDays(2).format(DateTimeFormatter.ofPattern(BookingPeriod.DATE_PATTERN));
        mvc.perform(
                        put("/bookings/1")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .accept(MediaType.APPLICATION_JSON_VALUE)
                                .content(
                                        "{\n" +
                                                "    \"startDate\": \"" + startDate + "\",\n" +
                                                "    \"endDate\": \"2023-08-248\"" +
                                                "}"
                                )
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.exception").exists())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.exception").value(InvalidBookingDateException.class.getSimpleName()))
                .andExpect(jsonPath("$.message").value("endDate must be a valid date"))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof InvalidBookingDateException))
                .andExpect(
                        result -> assertEquals(
                                "endDate must be a valid date", result.getResolvedException().getMessage()
                        )
                );
    }

    @Test
    public void givenBookingExistsThenUpdateBookingReturns200() throws Exception {
        final String startDate = LocalDate.now().plusDays(25).format(DateTimeFormatter.ofPattern(BookingPeriod.DATE_PATTERN));
        final String endDate = LocalDate.now().plusDays(32).format(DateTimeFormatter.ofPattern(BookingPeriod.DATE_PATTERN));
        final String updatedStartDate = LocalDate.now().plusDays(28).format(DateTimeFormatter.ofPattern(BookingPeriod.DATE_PATTERN));
        final String updatedEndDate = LocalDate.now().plusDays(40).format(DateTimeFormatter.ofPattern(BookingPeriod.DATE_PATTERN));
        final String bookingResponseContent = mvc.perform(
                post("/bookings")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(
                                "{\n" +
                                        "    \"startDate\": \"" + startDate + "\",\n" +
                                        "    \"endDate\": \"" + endDate + "\",\n" +
                                        "    \"propertyId\": 6\n" +
                                        "}"
                        )
        ).andReturn().getResponse().getContentAsString();

        final BookingResponse bookingResponse = objectMapper.readValue(bookingResponseContent, BookingResponse.class);
        mvc.perform(
                        put(String.format("/bookings/%s", bookingResponse.id()))
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .accept(MediaType.APPLICATION_JSON_VALUE)
                                .content(
                                        "{\n" +
                                                "    \"startDate\": \"" + updatedStartDate + "\",\n" +
                                                "    \"endDate\": \"" + updatedEndDate + "\"" +
                                                "}"
                                )
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.exception").doesNotExist())
                .andExpect(jsonPath("$.message").doesNotExist())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.id").value(bookingResponse.id()))
                .andExpect(jsonPath("$.startDate").exists())
                .andExpect(jsonPath("$.startDate").value(updatedStartDate))
                .andExpect(jsonPath("$.endDate").exists())
                .andExpect(jsonPath("$.endDate").value(updatedEndDate))
                .andExpect(jsonPath("$.property.id").exists())
                .andExpect(jsonPath("$.property.name").exists())
                .andExpect(jsonPath("$.status").exists())
                .andExpect(jsonPath("$.status").value(BookingStatus.ACTIVE.name()));
    }

    @Test
    public void givenUpdatedBookingOverlapsAnotherBookingOnTheSamePropertyThenUpdateBookingReturns409() throws Exception {
        final String bookingOneStartDate = LocalDate.now().plusDays(25).format(DateTimeFormatter.ofPattern(BookingPeriod.DATE_PATTERN));
        final String bookingOneEndDate = LocalDate.now().plusDays(32).format(DateTimeFormatter.ofPattern(BookingPeriod.DATE_PATTERN));
        final String bookingTwoStartDate = LocalDate.now().plusDays(32).format(DateTimeFormatter.ofPattern(BookingPeriod.DATE_PATTERN));
        final String bookingTwoEndDate = LocalDate.now().plusDays(40).format(DateTimeFormatter.ofPattern(BookingPeriod.DATE_PATTERN));
        final String bookingOneUpdatedStartDate = LocalDate.now().plusDays(28).format(DateTimeFormatter.ofPattern(BookingPeriod.DATE_PATTERN));
        final String bookingOneUpdatedEndDate = LocalDate.now().plusDays(36).format(DateTimeFormatter.ofPattern(BookingPeriod.DATE_PATTERN));

        final String bookingOneResponseContent = mvc.perform(
                post("/bookings")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(
                                "{\n" +
                                        "    \"startDate\": \"" + bookingOneStartDate + "\",\n" +
                                        "    \"endDate\": \"" + bookingOneEndDate + "\",\n" +
                                        "    \"propertyId\": 13\n" +
                                        "}"
                        )
        ).andReturn().getResponse().getContentAsString();

        final BookingResponse bookingOneResponse = objectMapper.readValue(bookingOneResponseContent, BookingResponse.class);

        mvc.perform(
                post("/bookings")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(
                                "{\n" +
                                        "    \"startDate\": \"" + bookingTwoStartDate + "\",\n" +
                                        "    \"endDate\": \"" + bookingTwoEndDate + "\",\n" +
                                        "    \"propertyId\": 13\n" +
                                        "}"
                        )
        ).andReturn().getResponse().getContentAsString();

        mvc.perform(
                        put(String.format("/bookings/%s", bookingOneResponse.id()))
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .accept(MediaType.APPLICATION_JSON_VALUE)
                                .content(
                                        "{\n" +
                                                "    \"startDate\": \"" + bookingOneUpdatedStartDate + "\",\n" +
                                                "    \"endDate\": \"" + bookingOneUpdatedEndDate + "\"" +
                                                "}"
                                )
                )
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.exception").exists())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.exception").value(PropertyUnavailableException.class.getSimpleName()))
                .andExpect(jsonPath("$.message").value(
                        String.format(
                                "Property with id %s is unavailable during the time you requested for booking.",
                                bookingOneResponse.property().id()
                        )
                ))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof PropertyUnavailableException))
                .andExpect(result ->
                        assertEquals(
                                String.format(
                                        "Property with id %s is unavailable during the time you requested for booking.",
                                        bookingOneResponse.property().id()
                                ),
                                result.getResolvedException().getMessage()
                        )
                );
    }

    @Test
    public void givenUpdatedBookingOverlapsABlockOnTheSamePropertyThenUpdateBookingReturns409() throws Exception {
        final String bookingStartDate = LocalDate.now().plusDays(25).format(DateTimeFormatter.ofPattern(BookingPeriod.DATE_PATTERN));
        final String bookingEndDate = LocalDate.now().plusDays(32).format(DateTimeFormatter.ofPattern(BookingPeriod.DATE_PATTERN));
        final String blockStartDate = LocalDate.now().plusDays(32).format(DateTimeFormatter.ofPattern(BookingPeriod.DATE_PATTERN));
        final String blockEndDate = LocalDate.now().plusDays(40).format(DateTimeFormatter.ofPattern(BookingPeriod.DATE_PATTERN));
        final String bookingUpdatedStartDate = LocalDate.now().plusDays(28).format(DateTimeFormatter.ofPattern(BookingPeriod.DATE_PATTERN));
        final String bookingUpdatedEndDate = LocalDate.now().plusDays(36).format(DateTimeFormatter.ofPattern(BookingPeriod.DATE_PATTERN));

        final String bookingResponseContent = mvc.perform(
                post("/bookings")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(
                                "{\n" +
                                        "    \"startDate\": \"" + bookingStartDate + "\",\n" +
                                        "    \"endDate\": \"" + bookingEndDate + "\",\n" +
                                        "    \"propertyId\": 14\n" +
                                        "}"
                        )
        ).andReturn().getResponse().getContentAsString();

        final BookingResponse bookingResponse = objectMapper.readValue(bookingResponseContent, BookingResponse.class);

        mvc.perform(
                post("/blocks")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(
                                "{\n" +
                                        "    \"startDate\": \"" + blockStartDate + "\",\n" +
                                        "    \"endDate\": \"" + blockEndDate + "\",\n" +
                                        "    \"propertyId\": 14,\n" +
                                        "    \"reasonId\": 1\n" +
                                        "}"
                        )
        ).andReturn().getResponse().getContentAsString();

        mvc.perform(
                        put(String.format("/bookings/%s", bookingResponse.id()))
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .accept(MediaType.APPLICATION_JSON_VALUE)
                                .content(
                                        "{\n" +
                                                "    \"startDate\": \"" + bookingUpdatedStartDate + "\",\n" +
                                                "    \"endDate\": \"" + bookingUpdatedEndDate + "\"" +
                                                "}"
                                )
                )
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.exception").exists())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.exception").value(PropertyBlockedException.class.getSimpleName()))
                .andExpect(jsonPath("$.message").value(
                        String.format(
                                "Property with id %s is blocked during the time you requested for booking.",
                                bookingResponse.property().id()
                        )
                ))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof PropertyBlockedException))
                .andExpect(result ->
                        assertEquals(
                                String.format(
                                        "Property with id %s is blocked during the time you requested for booking.",
                                        bookingResponse.property().id()
                                ),
                                result.getResolvedException().getMessage()
                        )
                );
    }

    @Test
    public void givenDeleteExistingBookingThenDeleteBookingReturns200AndBookingStatusIsCancelled() throws Exception {
        final String bookingStartDate = LocalDate.now().plusDays(25).format(DateTimeFormatter.ofPattern(BookingPeriod.DATE_PATTERN));
        final String bookingEndDate = LocalDate.now().plusDays(32).format(DateTimeFormatter.ofPattern(BookingPeriod.DATE_PATTERN));

        final String bookingResponseContent = mvc.perform(
                post("/bookings")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(
                                "{\n" +
                                        "    \"startDate\": \"" + bookingStartDate + "\",\n" +
                                        "    \"endDate\": \"" + bookingEndDate + "\",\n" +
                                        "    \"propertyId\": 18\n" +
                                        "}"
                        )
        ).andReturn().getResponse().getContentAsString();

        final BookingResponse bookingResponse = objectMapper.readValue(bookingResponseContent, BookingResponse.class);

        mvc.perform(
                        delete(String.format("/bookings/%s", bookingResponse.id()))
                                .accept(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.exception").doesNotExist())
                .andExpect(jsonPath("$.message").doesNotExist())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.id").value(bookingResponse.id()))
                .andExpect(jsonPath("$.startDate").exists())
                .andExpect(jsonPath("$.startDate").value(bookingStartDate))
                .andExpect(jsonPath("$.endDate").exists())
                .andExpect(jsonPath("$.endDate").value(bookingEndDate))
                .andExpect(jsonPath("$.property.id").exists())
                .andExpect(jsonPath("$.property.name").exists())
                .andExpect(jsonPath("$.status").exists())
                .andExpect(jsonPath("$.status").value(BookingStatus.CANCELLED.name()));
    }

    @Test
    public void givenDeletePreviouslyDeletedBookingThenDeleteBookingReturns404() throws Exception {
        final String bookingStartDate = LocalDate.now().plusDays(25).format(DateTimeFormatter.ofPattern(BookingPeriod.DATE_PATTERN));
        final String bookingEndDate = LocalDate.now().plusDays(32).format(DateTimeFormatter.ofPattern(BookingPeriod.DATE_PATTERN));

        final String bookingResponseContent = mvc.perform(
                post("/bookings")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(
                                "{\n" +
                                        "    \"startDate\": \"" + bookingStartDate + "\",\n" +
                                        "    \"endDate\": \"" + bookingEndDate + "\",\n" +
                                        "    \"propertyId\": 18\n" +
                                        "}"
                        )
        ).andReturn().getResponse().getContentAsString();

        final BookingResponse bookingResponse = objectMapper.readValue(bookingResponseContent, BookingResponse.class);

        mvc.perform(
                delete(String.format("/bookings/%s", bookingResponse.id()))
                        .accept(MediaType.APPLICATION_JSON_VALUE)
        );
        mvc.perform(
                        delete(String.format("/bookings/%s", bookingResponse.id()))
                                .accept(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.exception").exists())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.exception").value(BookingNotFoundException.class.getSimpleName()))
                .andExpect(jsonPath("$.message").value(
                        String.format("Booking with id %s not found", bookingResponse.id())
                ))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BookingNotFoundException))
                .andExpect(result ->
                        assertEquals(
                                String.format("Booking with id %s not found", bookingResponse.id()),
                                result.getResolvedException().getMessage()
                        )
                );
    }

    @Test
    public void givenUpdatePreviouslyDeletedBookingThenDeleteBookingReturns404() throws Exception {
        final String bookingStartDate = LocalDate.now().plusDays(25).format(DateTimeFormatter.ofPattern(BookingPeriod.DATE_PATTERN));
        final String bookingEndDate = LocalDate.now().plusDays(32).format(DateTimeFormatter.ofPattern(BookingPeriod.DATE_PATTERN));
        final String bookingUpdatedStartDate = LocalDate.now().plusDays(28).format(DateTimeFormatter.ofPattern(BookingPeriod.DATE_PATTERN));
        final String bookingUpdatedEndDate = LocalDate.now().plusDays(36).format(DateTimeFormatter.ofPattern(BookingPeriod.DATE_PATTERN));

        final String bookingResponseContent = mvc.perform(
                post("/bookings")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(
                                "{\n" +
                                        "    \"startDate\": \"" + bookingStartDate + "\",\n" +
                                        "    \"endDate\": \"" + bookingEndDate + "\",\n" +
                                        "    \"propertyId\": 18\n" +
                                        "}"
                        )
        ).andReturn().getResponse().getContentAsString();

        final BookingResponse bookingResponse = objectMapper.readValue(bookingResponseContent, BookingResponse.class);

        mvc.perform(
                delete(String.format("/bookings/%s", bookingResponse.id()))
                        .accept(MediaType.APPLICATION_JSON_VALUE)
        );
        mvc.perform(
                        put(String.format("/bookings/%s", bookingResponse.id()))
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .accept(MediaType.APPLICATION_JSON_VALUE)
                                .content(
                                        "{\n" +
                                                "    \"startDate\": \"" + bookingUpdatedStartDate + "\",\n" +
                                                "    \"endDate\": \"" + bookingUpdatedEndDate + "\"" +
                                                "}"
                                )
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.exception").exists())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.exception").value(BookingNotFoundException.class.getSimpleName()))
                .andExpect(jsonPath("$.message").value(
                        String.format("Booking with id %s not found", bookingResponse.id())
                ))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BookingNotFoundException))
                .andExpect(result ->
                        assertEquals(
                                String.format("Booking with id %s not found", bookingResponse.id()),
                                result.getResolvedException().getMessage()
                        )
                );
    }

    @Test
    public void givenReadPreviouslyDeletedBookingThenDeleteBookingReturns200() throws Exception {
        final String bookingStartDate = LocalDate.now().plusDays(25).format(DateTimeFormatter.ofPattern(BookingPeriod.DATE_PATTERN));
        final String bookingEndDate = LocalDate.now().plusDays(32).format(DateTimeFormatter.ofPattern(BookingPeriod.DATE_PATTERN));

        final String bookingResponseContent = mvc.perform(
                post("/bookings")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(
                                "{\n" +
                                        "    \"startDate\": \"" + bookingStartDate + "\",\n" +
                                        "    \"endDate\": \"" + bookingEndDate + "\",\n" +
                                        "    \"propertyId\": 18\n" +
                                        "}"
                        )
        ).andReturn().getResponse().getContentAsString();

        final BookingResponse bookingResponse = objectMapper.readValue(bookingResponseContent, BookingResponse.class);

        mvc.perform(
                delete(String.format("/bookings/%s", bookingResponse.id()))
                        .accept(MediaType.APPLICATION_JSON_VALUE)
        );
        mvc.perform(
                        get(String.format("/bookings/%s", bookingResponse.id())).accept(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.exception").doesNotExist())
                .andExpect(jsonPath("$.message").doesNotExist())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.id").value(bookingResponse.id()))
                .andExpect(jsonPath("$.startDate").exists())
                .andExpect(jsonPath("$.startDate").value(bookingStartDate))
                .andExpect(jsonPath("$.endDate").exists())
                .andExpect(jsonPath("$.endDate").value(bookingEndDate))
                .andExpect(jsonPath("$.property.id").exists())
                .andExpect(jsonPath("$.property.name").exists())
                .andExpect(jsonPath("$.status").exists())
                .andExpect(jsonPath("$.status").value(BookingStatus.CANCELLED.name()));
    }

    @Test
    public void givenReCreatePreviouslyDeletedBookingThenReCreateBookingReturns201() throws Exception {
        final String bookingStartDate = LocalDate.now().plusDays(25).format(DateTimeFormatter.ofPattern(BookingPeriod.DATE_PATTERN));
        final String bookingEndDate = LocalDate.now().plusDays(32).format(DateTimeFormatter.ofPattern(BookingPeriod.DATE_PATTERN));

        final String bookingResponseContent = mvc.perform(
                post("/bookings")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(
                                "{\n" +
                                        "    \"startDate\": \"" + bookingStartDate + "\",\n" +
                                        "    \"endDate\": \"" + bookingEndDate + "\",\n" +
                                        "    \"propertyId\": 15\n" +
                                        "}"
                        )
        ).andReturn().getResponse().getContentAsString();

        final BookingResponse bookingResponse = objectMapper.readValue(bookingResponseContent, BookingResponse.class);

        final ResultActions cancelBookingResultActions = mvc.perform(
                delete(String.format("/bookings/%s", bookingResponse.id()))
                        .accept(MediaType.APPLICATION_JSON_VALUE)
        );

        final BookingResponse cancelBookingResponse = objectMapper.readValue(
                cancelBookingResultActions.andReturn().getResponse().getContentAsString(),
                BookingResponse.class
        );

        final ResultActions reCreateBookingResultActions = mvc.perform(
                post(String.format("/bookings/%s", bookingResponse.id()))
                        .accept(MediaType.APPLICATION_JSON_VALUE)
        );

        final BookingResponse reCreateBookingResponse = objectMapper.readValue(
                reCreateBookingResultActions.andReturn().getResponse().getContentAsString(), BookingResponse.class
        );

        reCreateBookingResultActions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.exception").doesNotExist())
                .andExpect(jsonPath("$.message").doesNotExist())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.id").value(reCreateBookingResponse.id()))
                .andExpect(jsonPath("$.startDate").exists())
                .andExpect(jsonPath("$.startDate").value(bookingStartDate))
                .andExpect(jsonPath("$.endDate").exists())
                .andExpect(jsonPath("$.endDate").value(bookingEndDate))
                .andExpect(jsonPath("$.property.id").exists())
                .andExpect(jsonPath("$.property.name").exists())
                .andExpect(jsonPath("$.status").exists())
                .andExpect(jsonPath("$.status").value(BookingStatus.ACTIVE.name()));

        assertNotEquals(bookingResponse.id(), reCreateBookingResponse.id());
        assertEquals(bookingResponse.startDate(), reCreateBookingResponse.startDate());
        assertEquals(bookingResponse.endDate(), reCreateBookingResponse.endDate());
        assertEquals(bookingResponse.endDate(), reCreateBookingResponse.endDate());
        assertEquals(bookingResponse.status(), BookingStatus.ACTIVE.name());
        assertEquals(cancelBookingResponse.status(), BookingStatus.CANCELLED.name());
        assertEquals(cancelBookingResponse.property().id(), reCreateBookingResponse.property().id());
        assertEquals(reCreateBookingResponse.status(), BookingStatus.ACTIVE.name());
    }

    @Test
    public void givenReCreateBookingOverlapsAnotherBookingThenReCreateBookingReturns409() throws Exception {
        final String bookingStartDate = LocalDate.now().plusDays(25).format(DateTimeFormatter.ofPattern(BookingPeriod.DATE_PATTERN));
        final String bookingEndDate = LocalDate.now().plusDays(32).format(DateTimeFormatter.ofPattern(BookingPeriod.DATE_PATTERN));
        final String anotherBookingStartDate = LocalDate.now().plusDays(28).format(DateTimeFormatter.ofPattern(BookingPeriod.DATE_PATTERN));
        final String anotherBookingEndDate = LocalDate.now().plusDays(36).format(DateTimeFormatter.ofPattern(BookingPeriod.DATE_PATTERN));

        final String bookingResponseContent = mvc.perform(
                post("/bookings")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(
                                "{\n" +
                                        "    \"startDate\": \"" + bookingStartDate + "\",\n" +
                                        "    \"endDate\": \"" + bookingEndDate + "\",\n" +
                                        "    \"propertyId\": 19\n" +
                                        "}"
                        )
        ).andReturn().getResponse().getContentAsString();

        final BookingResponse bookingResponse = objectMapper.readValue(bookingResponseContent, BookingResponse.class);

        mvc.perform(
                delete(String.format("/bookings/%s", bookingResponse.id()))
                        .accept(MediaType.APPLICATION_JSON_VALUE)
        );

        mvc.perform(
                post("/bookings")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(
                                "{\n" +
                                        "    \"startDate\": \"" + anotherBookingStartDate + "\",\n" +
                                        "    \"endDate\": \"" + anotherBookingEndDate + "\",\n" +
                                        "    \"propertyId\": 19\n" +
                                        "}"
                        )
        );

        mvc.perform(
                        post(String.format("/bookings/%s", bookingResponse.id()))
                                .accept(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.exception").exists())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.exception").value(PropertyUnavailableException.class.getSimpleName()))
                .andExpect(jsonPath("$.message").value(
                        "Property with id 19 is unavailable during the time you requested for booking."
                ))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof PropertyUnavailableException))
                .andExpect(result ->
                        assertEquals(
                                "Property with id 19 is unavailable during the time you requested for booking.",
                                result.getResolvedException().getMessage()
                        )
                );
    }

    @Test
    public void givenReCreateBookingOverlapsABlockThenReCreateBookingReturns409() throws Exception {
        final String bookingStartDate = LocalDate.now().plusDays(25).format(DateTimeFormatter.ofPattern(BookingPeriod.DATE_PATTERN));
        final String bookingEndDate = LocalDate.now().plusDays(32).format(DateTimeFormatter.ofPattern(BookingPeriod.DATE_PATTERN));
        final String blockStartDate = LocalDate.now().plusDays(28).format(DateTimeFormatter.ofPattern(BookingPeriod.DATE_PATTERN));
        final String blockEndDate = LocalDate.now().plusDays(36).format(DateTimeFormatter.ofPattern(BookingPeriod.DATE_PATTERN));

        final String bookingResponseContent = mvc.perform(
                post("/bookings")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(
                                "{\n" +
                                        "    \"startDate\": \"" + bookingStartDate + "\",\n" +
                                        "    \"endDate\": \"" + bookingEndDate + "\",\n" +
                                        "    \"propertyId\": 20\n" +
                                        "}"
                        )
        ).andReturn().getResponse().getContentAsString();

        final BookingResponse bookingResponse = objectMapper.readValue(bookingResponseContent, BookingResponse.class);

        mvc.perform(
                delete(String.format("/bookings/%s", bookingResponse.id()))
                        .accept(MediaType.APPLICATION_JSON_VALUE)
        );

        mvc.perform(
                post("/blocks")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(
                                "{\n" +
                                        "    \"startDate\": \"" + blockStartDate + "\",\n" +
                                        "    \"endDate\": \"" + blockEndDate + "\",\n" +
                                        "    \"propertyId\": 20,\n" +
                                        "    \"reasonId\": 1\n" +
                                        "}"
                        )
        );

        mvc.perform(
                        post(String.format("/bookings/%s", bookingResponse.id()))
                                .accept(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.exception").exists())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.exception").value(PropertyBlockedException.class.getSimpleName()))
                .andExpect(jsonPath("$.message").value(
                        "Property with id 20 is blocked during the time you requested for booking."
                ))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof PropertyBlockedException))
                .andExpect(result ->
                        assertEquals(
                                "Property with id 20 is blocked during the time you requested for booking.",
                                result.getResolvedException().getMessage()
                        )
                );
    }

}
