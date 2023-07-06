package com.hostfully.bookings.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hostfully.bookings.application.response.BlockResponse;
import com.hostfully.bookings.application.response.BookingResponse;
import com.hostfully.bookings.domain.entity.block.BlockStatus;
import com.hostfully.bookings.domain.exception.*;
import com.hostfully.bookings.domain.value.BlockPeriod;
import com.hostfully.bookings.domain.value.BookingPeriod;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BlockControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void givenInvalidStartDateThenCreateBlockReturns400() throws Exception {
        final String endDate = LocalDate.now().plusDays(2).format(DateTimeFormatter.ofPattern(BlockPeriod.DATE_PATTERN));
        mvc.perform(
                post("/blocks")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(
                            "{\n" +
                            "    \"startDate\": \"2023-08-248\",\n" +
                            "    \"endDate\": \"" + endDate + "\",\n" +
                            "    \"propertyId\": 1,\n" +
                            "    \"reasonId\": 1\n" +
                            "}"
                        )
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.exception").exists())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.exception").value(InvalidBlockDateException.class.getSimpleName()))
                .andExpect(jsonPath("$.message").value("startDate must be a valid date"))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof InvalidBlockDateException))
                .andExpect(
                        result -> assertEquals(
                                "startDate must be a valid date", result.getResolvedException().getMessage()
                        )
                );
    }

    @Test
    public void givenInvalidEndDateThenCreateBlockReturns400() throws Exception {
        final String startDate = LocalDate.now().plusDays(2).format(DateTimeFormatter.ofPattern(BlockPeriod.DATE_PATTERN));
        mvc.perform(
                post("/blocks")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(
                            "{\n" +
                            "    \"startDate\": \"" + startDate + "\",\n" +
                            "    \"endDate\": \"2023-087-28\",\n" +
                            "    \"propertyId\": 1,\n" +
                            "    \"reasonId\": 1\n" +
                            "}"
                        )
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.exception").exists())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.exception").value(InvalidBlockDateException.class.getSimpleName()))
                .andExpect(jsonPath("$.message").value("endDate must be a valid date"))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof InvalidBlockDateException))
                .andExpect(result ->
                        assertEquals("endDate must be a valid date", result.getResolvedException().getMessage())
                );
    }

    @Test
    public void givenStartDateIsTodaysDateThenCreateBlockReturns400() throws Exception {
        mvc.perform(
                post("/blocks")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(
                            "{\n" +
                            "\"startDate\": \"" +
                            LocalDate.now().format(DateTimeFormatter.ofPattern(BlockPeriod.DATE_PATTERN))
                            + "\",\n" +
                            "    \"endDate\": \"" + LocalDate.now().plusDays(2).format(DateTimeFormatter.ofPattern(BlockPeriod.DATE_PATTERN)) + "\",\n" +
                            "    \"propertyId\": 1,\n" +
                            "    \"reasonId\": 1\n" +
                            "}"
                        )
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.exception").exists())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.exception").value(InvalidBlockDateException.class.getSimpleName()))
                .andExpect(jsonPath("$.message").value("startDate must be a future date"))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof InvalidBlockDateException))
                .andExpect(result ->
                        assertEquals("startDate must be a future date", result.getResolvedException().getMessage())
                );
    }

    @Test
    public void givenEndDateIsTodaysDateThenCreateBlockReturns400() throws Exception {
        mvc.perform(
                post("/blocks")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(
                                "{\n" +
                                "    \"startDate\": \"" + LocalDate.now().plusDays(2).format(DateTimeFormatter.ofPattern(BlockPeriod.DATE_PATTERN)) + "\",\n" +
                                "    \"endDate\": \"" + LocalDate.now().format(DateTimeFormatter.ofPattern(BlockPeriod.DATE_PATTERN)) + "\",\n" +
                                "    \"propertyId\": 1,\n" +
                                "    \"reeasonId\": 1\n" +
                                "}"
                        )
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.exception").exists())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.exception").value(InvalidBlockDateException.class.getSimpleName()))
                .andExpect(jsonPath("$.message").value("endDate must be a future date"))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof InvalidBlockDateException))
                .andExpect(result ->
                        assertEquals("endDate must be a future date", result.getResolvedException().getMessage())
                );
    }

    @Test
    public void givenEndDateIsSmallerThanStartDateThenCreateBlockReturns400() throws Exception {
        final String startDate = LocalDate.now().plusDays(5).format(DateTimeFormatter.ofPattern(BlockPeriod.DATE_PATTERN));
        final String endDate = LocalDate.now().plusDays(4).format(DateTimeFormatter.ofPattern(BlockPeriod.DATE_PATTERN));
        mvc.perform(
                post("/blocks")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(
                            "{\n" +
                            "    \"startDate\": \"" + startDate + "\",\n" +
                            "    \"endDate\": \"" + endDate + "\",\n" +
                            "    \"propertyId\": 1,\n" +
                            "    \"reasonId\": 1\n" +
                            "}"
                        )
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.exception").exists())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.exception").value(InvalidBlockDateException.class.getSimpleName()))
                .andExpect(jsonPath("$.message").value("endDate must be greater than the startDate"))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof InvalidBlockDateException))
                .andExpect(result ->
                        assertEquals("endDate must be greater than the startDate", result.getResolvedException().getMessage())
                );
    }

    @Test
    public void givenPropertyIdIsStringThenCreateBlockReturns200() throws Exception {
        final String startDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern(BlockPeriod.DATE_PATTERN));
        final String endDate = LocalDate.now().plusDays(5).format(DateTimeFormatter.ofPattern(BlockPeriod.DATE_PATTERN));
        mvc.perform(
                post("/blocks")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(
                            "{\n" +
                            "    \"startDate\": \"" + startDate + "\",\n" +
                            "    \"endDate\": \"" + endDate + "\",\n" +
                            "    \"propertyId\": \"1\",\n" +
                            "    \"reasonId\": \"1\"\n" +
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
                .andExpect(jsonPath("$.reason.id").exists())
                .andExpect(jsonPath("$.reason.id").value(1))
                .andExpect(jsonPath("$.reason.description").exists())
                .andExpect(jsonPath("$.status").exists())
                .andExpect(jsonPath("$.status").value(BlockStatus.ACTIVE.name()));
    }

    @Test
    public void givenPropertyIdIsNumericThenCreateBlockReturns200() throws Exception {
        final String startDate = LocalDate.now().plusDays(7).format(DateTimeFormatter.ofPattern(BlockPeriod.DATE_PATTERN));
        final String endDate = LocalDate.now().plusDays(9).format(DateTimeFormatter.ofPattern(BlockPeriod.DATE_PATTERN));
        mvc.perform(
                post("/blocks")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(
                            "{\n" +
                            "    \"startDate\": \"" + startDate + "\",\n" +
                            "    \"endDate\": \"" + endDate + "\",\n" +
                            "    \"propertyId\": 8,\n" +
                            "    \"reasonId\": 1\n" +
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
                .andExpect(jsonPath("$.reason.id").exists())
                .andExpect(jsonPath("$.reason.id").value(1))
                .andExpect(jsonPath("$.reason.description").exists())
                .andExpect(jsonPath("$.status").exists())
                .andExpect(jsonPath("$.status").value(BlockStatus.ACTIVE.name()));
    }

    @Test
    public void givenPropertyIdIsNonNumericStringThenCreateBlockReturns400() throws Exception {
        final String startDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern(BlockPeriod.DATE_PATTERN));
        final String endDate = LocalDate.now().plusDays(5).format(DateTimeFormatter.ofPattern(BlockPeriod.DATE_PATTERN));
        mvc.perform(
                post("/blocks")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(
                            "{\n" +
                            "    \"startDate\": \"" + startDate + "\",\n" +
                            "    \"endDate\": \"" + endDate + "\",\n" +
                            "    \"propertyId\": \"*1\",\n" +
                            "    \"reasonId\": \"1\"\n" +
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
    public void givenReasonIdIsNegativeNumericStringThenCreateBlockReturns400() throws Exception {
        final String startDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern(BlockPeriod.DATE_PATTERN));
        final String endDate = LocalDate.now().plusDays(5).format(DateTimeFormatter.ofPattern(BlockPeriod.DATE_PATTERN));
        mvc.perform(
                post("/blocks")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(
                            "{\n" +
                            "    \"startDate\": \"" + startDate + "\",\n" +
                            "    \"endDate\": \"" + endDate + "\",\n" +
                            "    \"propertyId\": \"1\",\n" +
                            "    \"reasonId\": \"-1\"\n" +
                            "}"
                        )
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.exception").exists())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.exception").value(InvalidEntityIdException.class.getSimpleName()))
                .andExpect(jsonPath("$.message").value("blockReasonId must be a positive number"))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof InvalidEntityIdException))
                .andExpect(result ->
                        assertEquals("blockReasonId must be a positive number", result.getResolvedException().getMessage())
                );
    }

    @Test
    public void givenReasonIdIsZeroThenCreateBlockReturns400() throws Exception {
        final String startDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern(BlockPeriod.DATE_PATTERN));
        final String endDate = LocalDate.now().plusDays(5).format(DateTimeFormatter.ofPattern(BlockPeriod.DATE_PATTERN));
        mvc.perform(
                post("/blocks")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(
                                "{\n" +
                                "    \"startDate\": \"" + startDate + "\",\n" +
                                "    \"endDate\": \"" + endDate + "\",\n" +
                                "    \"propertyId\": 1,\n" +
                                "    \"reasonId\": 0\n" +
                                "}"
                        )
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.exception").exists())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.exception").value(InvalidEntityIdException.class.getSimpleName()))
                .andExpect(jsonPath("$.message").value("blockReasonId must be a positive number"))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof InvalidEntityIdException))
                .andExpect(result ->
                        assertEquals("blockReasonId must be a positive number", result.getResolvedException().getMessage())
                );
    }

    @Test
    public void givenReasonIdIsStringThenCreateBlockReturns200() throws Exception {
        final String startDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern(BlockPeriod.DATE_PATTERN));
        final String endDate = LocalDate.now().plusDays(5).format(DateTimeFormatter.ofPattern(BlockPeriod.DATE_PATTERN));
        mvc.perform(
                post("/blocks")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(
                            "{\n" +
                            "    \"startDate\": \"" + startDate + "\",\n" +
                            "    \"endDate\": \"" + endDate + "\",\n" +
                            "    \"propertyId\": \"1\",\n" +
                            "    \"reasonId\": \"1\"\n" +
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
                .andExpect(jsonPath("$.reason.id").exists())
                .andExpect(jsonPath("$.reason.id").value(1))
                .andExpect(jsonPath("$.reason.description").exists())
                .andExpect(jsonPath("$.status").exists())
                .andExpect(jsonPath("$.status").value(BlockStatus.ACTIVE.name()));
    }

    @Test
    public void givenReasonIdIsNumericThenCreateBlockReturns200() throws Exception {
        final String startDate = LocalDate.now().plusDays(7).format(DateTimeFormatter.ofPattern(BlockPeriod.DATE_PATTERN));
        final String endDate = LocalDate.now().plusDays(9).format(DateTimeFormatter.ofPattern(BlockPeriod.DATE_PATTERN));
        mvc.perform(
                post("/blocks")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(
                            "{\n" +
                            "    \"startDate\": \"" + startDate + "\",\n" +
                            "    \"endDate\": \"" + endDate + "\",\n" +
                            "    \"propertyId\": 8,\n" +
                            "    \"reasonId\": 1\n" +
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
                .andExpect(jsonPath("$.reason.id").exists())
                .andExpect(jsonPath("$.reason.id").value(1))
                .andExpect(jsonPath("$.reason.description").exists())
                .andExpect(jsonPath("$.status").exists())
                .andExpect(jsonPath("$.status").value(BlockStatus.ACTIVE.name()));
    }

    @Test
    public void givenReasonIdIsNonNumericStringThenCreateBlockReturns400() throws Exception {
        final String startDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern(BlockPeriod.DATE_PATTERN));
        final String endDate = LocalDate.now().plusDays(5).format(DateTimeFormatter.ofPattern(BlockPeriod.DATE_PATTERN));
        mvc.perform(
                post("/blocks")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(
                            "{\n" +
                            "    \"startDate\": \"" + startDate + "\",\n" +
                            "    \"endDate\": \"" + endDate + "\",\n" +
                            "    \"propertyId\": \"1\",\n" +
                            "    \"reasonId\": \"*1\"\n" +
                            "}"
                        )
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.exception").exists())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.exception").value(InvalidEntityIdException.class.getSimpleName()))
                .andExpect(jsonPath("$.message").value("blockReasonId must be a numeric entity"))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof InvalidEntityIdException))
                .andExpect(result ->
                        assertEquals("blockReasonId must be a numeric entity", result.getResolvedException().getMessage())
                );
    }

    @Test
    public void givenPropertyIdIsNegativeNumericStringThenCreateBlockReturns400() throws Exception {
        final String startDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern(BlockPeriod.DATE_PATTERN));
        final String endDate = LocalDate.now().plusDays(5).format(DateTimeFormatter.ofPattern(BlockPeriod.DATE_PATTERN));
        mvc.perform(
                post("/blocks")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(
                            "{\n" +
                            "    \"startDate\": \"" + startDate + "\",\n" +
                            "    \"endDate\": \"" + endDate + "\",\n" +
                            "    \"propertyId\": \"-1\",\n" +
                            "    \"reasonId\": \"1\"\n" +
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
    public void givenPropertyIdIsZeroThenCreateBlockReturns400() throws Exception {
        final String startDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern(BlockPeriod.DATE_PATTERN));
        final String endDate = LocalDate.now().plusDays(5).format(DateTimeFormatter.ofPattern(BlockPeriod.DATE_PATTERN));
        mvc.perform(
                post("/blocks")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(
                                "{\n" +
                                "    \"startDate\": \"" + startDate + "\",\n" +
                                "    \"endDate\": \"" + endDate + "\",\n" +
                                "    \"propertyId\": 0,\n" +
                                "    \"reasonId\": 1\n" +
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
    public void givenBlockExistsThenReadBlockReturns200() throws Exception {
        final String startDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern(BlockPeriod.DATE_PATTERN));
        final String endDate = LocalDate.now().plusDays(5).format(DateTimeFormatter.ofPattern(BlockPeriod.DATE_PATTERN));
        final String blockResponseContent = mvc.perform(
                post("/blocks")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(
                                "{\n" +
                                "    \"startDate\": \"" + startDate + "\",\n" +
                                "    \"endDate\": \"" + endDate + "\",\n" +
                                "    \"propertyId\": 3,\n" +
                                "    \"reasonId\": 1\n" +
                                "}"
                        )
        ).andReturn().getResponse().getContentAsString();

        final BlockResponse blockResponse = objectMapper.readValue(blockResponseContent, BlockResponse.class);

        mvc.perform(
                get(String.format("/blocks/%s", blockResponse.id())).accept(MediaType.APPLICATION_JSON_VALUE)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.exception").doesNotExist())
                .andExpect(jsonPath("$.message").doesNotExist())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.id").value(blockResponse.id()))
                .andExpect(jsonPath("$.startDate").exists())
                .andExpect(jsonPath("$.startDate").value(startDate))
                .andExpect(jsonPath("$.endDate").exists())
                .andExpect(jsonPath("$.endDate").value(endDate))
                .andExpect(jsonPath("$.property.id").exists())
                .andExpect(jsonPath("$.property.id").value(3))
                .andExpect(jsonPath("$.property.name").exists())
                .andExpect(jsonPath("$.reason.id").exists())
                .andExpect(jsonPath("$.reason.id").value(1))
                .andExpect(jsonPath("$.reason.description").exists())
                .andExpect(jsonPath("$.status").exists())
                .andExpect(jsonPath("$.status").value(BlockStatus.ACTIVE.name()));
    }

    @Test
    public void givenBlockDoesNotExistThenReadBlockReturns404() throws Exception {

        mvc.perform(
                get("/blocks/845").accept(MediaType.APPLICATION_JSON_VALUE)
        )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.exception").exists())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.exception").value(BlockNotFoundException.class.getSimpleName()))
                .andExpect(jsonPath("$.message").value(
                        "Block with id 845 not found"
                ))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BlockNotFoundException))
                .andExpect(result ->
                        assertEquals(
                                "Block with id 845 not found",
                                result.getResolvedException().getMessage()
                        )
                );
    }

    @Test
    public void givenNonExistentPropertyForBlockThenCreateBlockReturns404() throws Exception {
        final String startDate = LocalDate.now().plusDays(33).format(DateTimeFormatter.ofPattern(BlockPeriod.DATE_PATTERN));
        final String endDate = LocalDate.now().plusDays(45).format(DateTimeFormatter.ofPattern(BlockPeriod.DATE_PATTERN));
        mvc.perform(
                        post("/blocks")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .accept(MediaType.APPLICATION_JSON_VALUE)
                                .content(
                                        "{\n" +
                                                "    \"startDate\": \"" + startDate + "\",\n" +
                                                "    \"endDate\": \"" + endDate + "\",\n" +
                                                "    \"propertyId\": 75,\n" +
                                                "    \"reasonId\": 1\n" +
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
    public void givenNonExistentReasonForBlockThenCreateBlockReturns404() throws Exception {
        final String startDate = LocalDate.now().plusDays(33).format(DateTimeFormatter.ofPattern(BlockPeriod.DATE_PATTERN));
        final String endDate = LocalDate.now().plusDays(45).format(DateTimeFormatter.ofPattern(BlockPeriod.DATE_PATTERN));
        mvc.perform(
                post("/blocks")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(
                            "{\n" +
                            "    \"startDate\": \"" + startDate + "\",\n" +
                            "    \"endDate\": \"" + endDate + "\",\n" +
                            "    \"propertyId\": 1,\n" +
                            "    \"reasonId\": 845\n" +
                            "}"
                        )
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.exception").exists())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.exception").value(BlockReasonNotFoundException.class.getSimpleName()))
                .andExpect(jsonPath("$.message").value(
                        String.format("BlockReason with id %s not found", 845)
                ))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BlockReasonNotFoundException))
                .andExpect(result ->
                        assertEquals(
                                String.format("BlockReason with id %s not found", 845),
                                result.getResolvedException().getMessage()
                        )
                );
    }

    @Test
    public void givenBlocksExistThenListBlocksReturnsAllWithStatusCode200() throws Exception {
        final String startDate = LocalDate.now().plusDays(33).format(DateTimeFormatter.ofPattern(BlockPeriod.DATE_PATTERN));
        final String endDate = LocalDate.now().plusDays(45).format(DateTimeFormatter.ofPattern(BlockPeriod.DATE_PATTERN));
        mvc.perform(
                post("/blocks")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(
                                "{\n" +
                                "    \"startDate\": \"" + startDate + "\",\n" +
                                "    \"endDate\": \"" + endDate + "\",\n" +
                                "    \"propertyId\": 4,\n" +
                                "    \"reasonId\": 1\n" +
                                "}"
                        )
        );

        mvc.perform(
                        get("/blocks").accept(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.exception").doesNotExist())
                .andExpect(jsonPath("$.message").doesNotExist())
                .andExpect(jsonPath("$.*.id").exists())
                .andExpect(jsonPath("$.*.startDate").exists())
                .andExpect(jsonPath("$.*.endDate").exists())
                .andExpect(jsonPath("$.*.property.id").exists())
                .andExpect(jsonPath("$.*.property.name").exists())
                .andExpect(jsonPath("$.*.reason.id").exists())
                .andExpect(jsonPath("$.*.reason.description").exists())
                .andExpect(jsonPath("$.*.status").exists());
    }

    @Test
    public void givenInvalidStartDateThenUpdateBlockReturns400() throws Exception {
        final String endDate = LocalDate.now().plusDays(2).format(DateTimeFormatter.ofPattern(BlockPeriod.DATE_PATTERN));

        mvc.perform(
                put("/blocks/1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(
                            "{\n" +
                            "    \"startDate\": \"2023-08-248\",\n" +
                            "    \"endDate\": \"" + endDate + "\"," +
                            "    \"reasonId\": 1" +
                            "}"
                        )
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.exception").exists())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.exception").value(InvalidBlockDateException.class.getSimpleName()))
                .andExpect(jsonPath("$.message").value("startDate must be a valid date"))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof InvalidBlockDateException))
                .andExpect(
                        result -> assertEquals(
                                "startDate must be a valid date", result.getResolvedException().getMessage()
                        )
                );
    }

    @Test
    public void givenInvalidEndDateThenUpdateBlockReturns400() throws Exception {
        final String startDate = LocalDate.now().plusDays(2).format(DateTimeFormatter.ofPattern(BlockPeriod.DATE_PATTERN));
        mvc.perform(
                put("/blocks/1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(
                                "{\n" +
                                "    \"startDate\": \"" + startDate + "\",\n" +
                                "    \"endDate\": \"2023-08-248\"," +
                                "    \"reasonId\": \"1\"" +
                                "}"
                        )
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.exception").exists())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.exception").value(InvalidBlockDateException.class.getSimpleName()))
                .andExpect(jsonPath("$.message").value("endDate must be a valid date"))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof InvalidBlockDateException))
                .andExpect(
                        result -> assertEquals(
                                "endDate must be a valid date", result.getResolvedException().getMessage()
                        )
                );
    }

    @Test
    public void givenInvalidReasonIdThenUpdateBlockReturns400() throws Exception {
        final String startDate = LocalDate.now().plusDays(2).format(DateTimeFormatter.ofPattern(BlockPeriod.DATE_PATTERN));
        final String endDate = LocalDate.now().plusDays(4).format(DateTimeFormatter.ofPattern(BlockPeriod.DATE_PATTERN));
        mvc.perform(
                put("/blocks/1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(
                                "{\n" +
                                "    \"startDate\": \"" + startDate + "\",\n" +
                                "    \"endDate\": \"" + endDate + "\"," +
                                "    \"reasonId\": \"*1\"" +
                                "}"
                        )
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.exception").exists())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.exception").value(InvalidEntityIdException.class.getSimpleName()))
                .andExpect(jsonPath("$.message").value("blockReasonId must be a numeric entity"))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof InvalidEntityIdException))
                .andExpect(
                        result -> assertEquals(
                                "blockReasonId must be a numeric entity", result.getResolvedException().getMessage()
                        )
                );
    }

    @Test
    public void givenBlockExistsThenUpdateBlockReturns200() throws Exception {
        final String startDate = LocalDate.now().plusDays(25).format(DateTimeFormatter.ofPattern(BlockPeriod.DATE_PATTERN));
        final String endDate = LocalDate.now().plusDays(32).format(DateTimeFormatter.ofPattern(BlockPeriod.DATE_PATTERN));
        final String updatedStartDate = LocalDate.now().plusDays(28).format(DateTimeFormatter.ofPattern(BlockPeriod.DATE_PATTERN));
        final String updatedEndDate = LocalDate.now().plusDays(40).format(DateTimeFormatter.ofPattern(BlockPeriod.DATE_PATTERN));
        final String blockResponseContent = mvc.perform(
                post("/blocks")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(
                                "{\n" +
                                        "    \"startDate\": \"" + startDate + "\",\n" +
                                        "    \"endDate\": \"" + endDate + "\",\n" +
                                        "    \"propertyId\": 6,\n" +
                                        "    \"reasonId\": 1\n" +
                                        "}"
                        )
        ).andReturn().getResponse().getContentAsString();

        final BlockResponse blockResponse = objectMapper.readValue(blockResponseContent, BlockResponse.class);
        mvc.perform(
                put(String.format("/blocks/%s", blockResponse.id()))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(
                                "{\n" +
                                "    \"startDate\": \"" + updatedStartDate + "\",\n" +
                                "    \"endDate\": \"" + updatedEndDate + "\"," +
                                "    \"reasonId\": \"1\"" +
                                "}"
                        )
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.exception").doesNotExist())
                .andExpect(jsonPath("$.message").doesNotExist())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.id").value(blockResponse.id()))
                .andExpect(jsonPath("$.startDate").exists())
                .andExpect(jsonPath("$.startDate").value(updatedStartDate))
                .andExpect(jsonPath("$.endDate").exists())
                .andExpect(jsonPath("$.endDate").value(updatedEndDate))
                .andExpect(jsonPath("$.property.id").exists())
                .andExpect(jsonPath("$.property.name").exists())
                .andExpect(jsonPath("$.reason.id").exists())
                .andExpect(jsonPath("$.reason.id").value(1))
                .andExpect(jsonPath("$.reason.description").exists())
                .andExpect(jsonPath("$.status").exists())
                .andExpect(jsonPath("$.status").value(BlockStatus.ACTIVE.name()));
    }

    @Test
    public void givenUpdatedBlockOverlapsAnotherBlockOnTheSamePropertyThenUpdateBlockReturns201() throws Exception {
        final String blockOneStartDate = LocalDate.now().plusDays(25).format(DateTimeFormatter.ofPattern(BlockPeriod.DATE_PATTERN));
        final String blockOneEndDate = LocalDate.now().plusDays(32).format(DateTimeFormatter.ofPattern(BlockPeriod.DATE_PATTERN));
        final String blockTwoStartDate = LocalDate.now().plusDays(32).format(DateTimeFormatter.ofPattern(BlockPeriod.DATE_PATTERN));
        final String blockTwoEndDate = LocalDate.now().plusDays(40).format(DateTimeFormatter.ofPattern(BlockPeriod.DATE_PATTERN));
        final String blockOneUpdatedStartDate = LocalDate.now().plusDays(28).format(DateTimeFormatter.ofPattern(BlockPeriod.DATE_PATTERN));
        final String blockOneUpdatedEndDate = LocalDate.now().plusDays(36).format(DateTimeFormatter.ofPattern(BlockPeriod.DATE_PATTERN));

        final String blockOneResponseContent = mvc.perform(
                post("/blocks")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(
                                "{\n" +
                                "    \"startDate\": \"" + blockOneStartDate + "\",\n" +
                                "    \"endDate\": \"" + blockOneEndDate + "\",\n" +
                                "    \"propertyId\": 13,\n" +
                                "    \"reasonId\": 1\n" +
                                "}"
                        )
        ).andReturn().getResponse().getContentAsString();

        final BlockResponse blockOneResponse = objectMapper.readValue(blockOneResponseContent, BlockResponse.class);

        mvc.perform(
                post("/blocks")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(
                                "{\n" +
                                "    \"startDate\": \"" + blockTwoStartDate + "\",\n" +
                                "    \"endDate\": \"" + blockTwoEndDate + "\",\n" +
                                "    \"propertyId\": 13,\n" +
                                "    \"reasonId\": 1\n" +
                                "}"
                        )
        ).andReturn().getResponse().getContentAsString();

        mvc.perform(
                put(String.format("/blocks/%s", blockOneResponse.id()))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(
                                "{\n" +
                                "    \"startDate\": \"" + blockOneUpdatedStartDate + "\",\n" +
                                "    \"endDate\": \"" + blockOneUpdatedEndDate + "\"," +
                                "    \"reasonId\": \"3\"" +
                                "}"
                        )
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.exception").doesNotExist())
                .andExpect(jsonPath("$.message").doesNotExist())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.id").value(blockOneResponse.id()))
                .andExpect(jsonPath("$.startDate").exists())
                .andExpect(jsonPath("$.startDate").value(blockOneUpdatedStartDate))
                .andExpect(jsonPath("$.endDate").exists())
                .andExpect(jsonPath("$.endDate").value(blockOneUpdatedEndDate))
                .andExpect(jsonPath("$.property.id").exists())
                .andExpect(jsonPath("$.property.name").exists())
                .andExpect(jsonPath("$.reason.id").exists())
                .andExpect(jsonPath("$.reason.id").value(3))
                .andExpect(jsonPath("$.reason.description").exists())
                .andExpect(jsonPath("$.status").exists())
                .andExpect(jsonPath("$.status").value(BlockStatus.ACTIVE.name()));
    }

    @Test
    public void givenDeleteExistingBlockThenDeleteBlockReturns200AndBlockStatusIsCancelled() throws Exception {
        final String blockStartDate = LocalDate.now().plusDays(25).format(DateTimeFormatter.ofPattern(BlockPeriod.DATE_PATTERN));
        final String blockEndDate = LocalDate.now().plusDays(32).format(DateTimeFormatter.ofPattern(BlockPeriod.DATE_PATTERN));

        final String blockResponseContent = mvc.perform(
                post("/blocks")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(
                                "{\n" +
                                "    \"startDate\": \"" + blockStartDate + "\",\n" +
                                "    \"endDate\": \"" + blockEndDate + "\",\n" +
                                "    \"propertyId\": 18,\n" +
                                "    \"reasonId\": 1\n" +
                                "}"
                        )
        ).andReturn().getResponse().getContentAsString();

        final BlockResponse blockResponse = objectMapper.readValue(blockResponseContent, BlockResponse.class);

        mvc.perform(
                delete(String.format("/blocks/%s", blockResponse.id()))
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.exception").doesNotExist())
                .andExpect(jsonPath("$.message").doesNotExist())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.id").value(blockResponse.id()))
                .andExpect(jsonPath("$.startDate").exists())
                .andExpect(jsonPath("$.startDate").value(blockStartDate))
                .andExpect(jsonPath("$.endDate").exists())
                .andExpect(jsonPath("$.endDate").value(blockEndDate))
                .andExpect(jsonPath("$.property.id").exists())
                .andExpect(jsonPath("$.property.id").value(blockResponse.property().id()))
                .andExpect(jsonPath("$.property.name").exists())
                .andExpect(jsonPath("$.reason.id").exists())
                .andExpect(jsonPath("$.reason.id").value(blockResponse.reason().id()))
                .andExpect(jsonPath("$.reason.description").exists())
                .andExpect(jsonPath("$.status").exists())
                .andExpect(jsonPath("$.status").value(BlockStatus.CANCELLED.name()));
    }

    @Test
    public void givenDeletePreviouslyDeletedBlockThenDeleteBlockReturns404() throws Exception {
        final String blockStartDate = LocalDate.now().plusDays(25).format(DateTimeFormatter.ofPattern(BlockPeriod.DATE_PATTERN));
        final String blockEndDate = LocalDate.now().plusDays(32).format(DateTimeFormatter.ofPattern(BlockPeriod.DATE_PATTERN));

        final String blockResponseContent = mvc.perform(
                post("/blocks")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(
                                "{\n" +
                                "    \"startDate\": \"" + blockStartDate + "\",\n" +
                                "    \"endDate\": \"" + blockEndDate + "\",\n" +
                                "    \"propertyId\": 18,\n" +
                                "    \"reasonId\": 1\n" +
                                "}"
                        )
        ).andReturn().getResponse().getContentAsString();

        final BlockResponse blockResponse = objectMapper.readValue(blockResponseContent, BlockResponse.class);

        mvc.perform(
                delete(String.format("/blocks/%s", blockResponse.id()))
                        .accept(MediaType.APPLICATION_JSON_VALUE)
        );
        mvc.perform(
                        delete(String.format("/blocks/%s", blockResponse.id()))
                                .accept(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.exception").exists())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.exception").value(BlockNotFoundException.class.getSimpleName()))
                .andExpect(jsonPath("$.message").value(
                        String.format("Block with id %s not found", blockResponse.id())
                ))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BlockNotFoundException))
                .andExpect(result ->
                        assertEquals(
                                String.format("Block with id %s not found", blockResponse.id()),
                                result.getResolvedException().getMessage()
                        )
                );
    }

    @Test
    public void givenUpdatePreviouslyDeletedBlockThenDeleteBlockReturns404() throws Exception {
        final String blockStartDate = LocalDate.now().plusDays(25).format(DateTimeFormatter.ofPattern(BlockPeriod.DATE_PATTERN));
        final String blockEndDate = LocalDate.now().plusDays(32).format(DateTimeFormatter.ofPattern(BlockPeriod.DATE_PATTERN));
        final String blockUpdatedStartDate = LocalDate.now().plusDays(28).format(DateTimeFormatter.ofPattern(BlockPeriod.DATE_PATTERN));
        final String blockUpdatedEndDate = LocalDate.now().plusDays(36).format(DateTimeFormatter.ofPattern(BlockPeriod.DATE_PATTERN));

        final String blockResponseContent = mvc.perform(
                post("/blocks")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(
                                "{\n" +
                                "    \"startDate\": \"" + blockStartDate + "\",\n" +
                                "    \"endDate\": \"" + blockEndDate + "\",\n" +
                                "    \"propertyId\": 18,\n" +
                                "    \"reasonId\": 1\n" +
                                "}"
                        )
        ).andReturn().getResponse().getContentAsString();

        final BlockResponse blockResponse = objectMapper.readValue(blockResponseContent, BlockResponse.class);

        mvc.perform(
                delete(String.format("/blocks/%s", blockResponse.id()))
                        .accept(MediaType.APPLICATION_JSON_VALUE)
        );
        mvc.perform(
                put(String.format("/blocks/%s", blockResponse.id()))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(
                            "{\n" +
                            "    \"startDate\": \"" + blockUpdatedStartDate + "\",\n" +
                            "    \"endDate\": \"" + blockUpdatedEndDate + "\"," +
                            "    \"reasonId\": \"2\"" +
                            "}"
                        )
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.exception").exists())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.exception").value(BlockNotFoundException.class.getSimpleName()))
                .andExpect(jsonPath("$.message").value(
                        String.format("Block with id %s not found", blockResponse.id())
                ))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BlockNotFoundException))
                .andExpect(result ->
                        assertEquals(
                                String.format("Block with id %s not found", blockResponse.id()),
                                result.getResolvedException().getMessage()
                        )
                );
    }

    @Test
    public void givenReadPreviouslyDeletedBlockThenDeleteBlockReturns200() throws Exception {
        final String blockStartDate = LocalDate.now().plusDays(25).format(DateTimeFormatter.ofPattern(BlockPeriod.DATE_PATTERN));
        final String blockEndDate = LocalDate.now().plusDays(32).format(DateTimeFormatter.ofPattern(BlockPeriod.DATE_PATTERN));

        final String blockResponseContent = mvc.perform(
                post("/blocks")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(
                                "{\n" +
                                "    \"startDate\": \"" + blockStartDate + "\",\n" +
                                "    \"endDate\": \"" + blockEndDate + "\",\n" +
                                "    \"propertyId\": 18,\n" +
                                "    \"reasonId\": 3\n" +
                                "}"
                        )
        ).andReturn().getResponse().getContentAsString();

        final BlockResponse blockResponse = objectMapper.readValue(blockResponseContent, BlockResponse.class);

        mvc.perform(
                delete(String.format("/blocks/%s", blockResponse.id()))
                        .accept(MediaType.APPLICATION_JSON_VALUE)
        );
        mvc.perform(
                        get(String.format("/blocks/%s", blockResponse.id())).accept(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.exception").doesNotExist())
                .andExpect(jsonPath("$.message").doesNotExist())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.id").value(blockResponse.id()))
                .andExpect(jsonPath("$.startDate").exists())
                .andExpect(jsonPath("$.startDate").value(blockStartDate))
                .andExpect(jsonPath("$.endDate").exists())
                .andExpect(jsonPath("$.endDate").value(blockEndDate))
                .andExpect(jsonPath("$.property.id").exists())
                .andExpect(jsonPath("$.property.id").value(blockResponse.property().id()))
                .andExpect(jsonPath("$.property.name").exists())
                .andExpect(jsonPath("$.reason.id").exists())
                .andExpect(jsonPath("$.reason.id").value(blockResponse.reason().id()))
                .andExpect(jsonPath("$.reason.description").exists())
                .andExpect(jsonPath("$.status").exists())
                .andExpect(jsonPath("$.status").value(BlockStatus.CANCELLED.name()));
    }

    @Test
    public void givenBlockOverlapsToExistingBookingsThenCreateBlockReturns200AndCancelsBookingsOnProperty() throws Exception {
        final String blockStartDate = LocalDate.now().plusDays(28).format(DateTimeFormatter.ofPattern(BlockPeriod.DATE_PATTERN));
        final String blockEndDate = LocalDate.now().plusDays(36).format(DateTimeFormatter.ofPattern(BlockPeriod.DATE_PATTERN));
        final String bookingOneStartDate = LocalDate.now().plusDays(25).format(DateTimeFormatter.ofPattern(BookingPeriod.DATE_PATTERN));
        final String bookingOneEndDate = LocalDate.now().plusDays(32).format(DateTimeFormatter.ofPattern(BookingPeriod.DATE_PATTERN));
        final String bookingTwoStartDate = LocalDate.now().plusDays(32).format(DateTimeFormatter.ofPattern(BookingPeriod.DATE_PATTERN));
        final String bookingTwoEndDate = LocalDate.now().plusDays(39).format(DateTimeFormatter.ofPattern(BookingPeriod.DATE_PATTERN));

        final String bookingOneResponseContent = mvc.perform(
                post("/bookings")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(
                                "{\n" +
                                "    \"startDate\": \"" + bookingOneStartDate + "\",\n" +
                                "    \"endDate\": \"" + bookingOneEndDate + "\",\n" +
                                "    \"propertyId\": 21\n" +
                                "}"
                        )
        ).andReturn().getResponse().getContentAsString();

        final String bookingTwoResponseContent = mvc.perform(
                post("/bookings")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(
                                "{\n" +
                                "    \"startDate\": \"" + bookingTwoStartDate + "\",\n" +
                                "    \"endDate\": \"" + bookingTwoEndDate + "\",\n" +
                                "    \"propertyId\": 21\n" +
                                "}"
                        )
        ).andReturn().getResponse().getContentAsString();

        mvc.perform(
                post("/blocks")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(
                                "{\n" +
                                "    \"startDate\": \"" + blockStartDate + "\",\n" +
                                "    \"endDate\": \"" + blockEndDate + "\",\n" +
                                "    \"propertyId\": 21,\n" +
                                "    \"reasonId\": 3\n" +
                                "}"
                        )
        );

        final BookingResponse bookingOneResponse = objectMapper.readValue(bookingOneResponseContent, BookingResponse.class);
        final BookingResponse bookingTwoResponse = objectMapper.readValue(bookingTwoResponseContent, BookingResponse.class);

        mvc.perform(
                        get(String.format("/bookings/%s", bookingOneResponse.id())).accept(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.exception").doesNotExist())
                .andExpect(jsonPath("$.message").doesNotExist())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.startDate").exists())
                .andExpect(jsonPath("$.startDate").value(bookingOneResponse.startDate()))
                .andExpect(jsonPath("$.endDate").exists())
                .andExpect(jsonPath("$.endDate").value(bookingOneResponse.endDate()))
                .andExpect(jsonPath("$.property.id").exists())
                .andExpect(jsonPath("$.property.id").value(bookingOneResponse.property().id()))
                .andExpect(jsonPath("$.property.name").exists())
                .andExpect(jsonPath("$.status").exists())
                .andExpect(jsonPath("$.status").value(BlockStatus.CANCELLED.name()));

        mvc.perform(
                        get(String.format("/bookings/%s", bookingTwoResponse.id())).accept(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.exception").doesNotExist())
                .andExpect(jsonPath("$.message").doesNotExist())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.startDate").exists())
                .andExpect(jsonPath("$.startDate").value(bookingTwoResponse.startDate()))
                .andExpect(jsonPath("$.endDate").exists())
                .andExpect(jsonPath("$.endDate").value(bookingTwoResponse.endDate()))
                .andExpect(jsonPath("$.property.id").exists())
                .andExpect(jsonPath("$.property.id").value(bookingTwoResponse.property().id()))
                .andExpect(jsonPath("$.property.name").exists())
                .andExpect(jsonPath("$.status").exists())
                .andExpect(jsonPath("$.status").value(BlockStatus.CANCELLED.name()));
    }
}
