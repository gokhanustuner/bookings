package com.hostfully.bookings.domain.value;

import com.hostfully.bookings.domain.exception.InvalidBlockDateException;
import org.apache.commons.validator.routines.CalendarValidator;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public record BlockPeriod(Date startDate, Date endDate) {
    public static String DATE_PATTERN = "yyyy-MM-dd";
    private final static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static BlockPeriod of(final String startDate, final String endDate) {
        final Calendar startDateCalendar = CalendarValidator.getInstance().validate(startDate, DATE_PATTERN);
        final Calendar endDateCalendar = CalendarValidator.getInstance().validate(endDate, DATE_PATTERN);

        if (startDateCalendar == null)
            throw new InvalidBlockDateException("startDate must be a valid date");

        if (!startDateCalendar.after(Calendar.getInstance()))
            throw new InvalidBlockDateException("startDate must be a future date");

        if (endDateCalendar == null)
            throw new InvalidBlockDateException("endDate must be a valid date");

        if (!endDateCalendar.after(Calendar.getInstance()))
            throw new InvalidBlockDateException("endDate must be a future date");

        if (!endDateCalendar.after(startDateCalendar))
            throw new InvalidBlockDateException("endDate must be greater than the startDate");

        return new BlockPeriod(parse(startDate), parse(endDate));
    }

    public static BlockPeriod of(final Date startDate, final Date endDate) {
        return new BlockPeriod(startDate, endDate);
    }

    private static Date parse(final String date) {
        try {
            return dateFormat.parse(date);
        } catch (final ParseException e) {
            throw new InvalidBlockDateException("Invalid date input to BlockPeriod", e);
        }
    }
}
