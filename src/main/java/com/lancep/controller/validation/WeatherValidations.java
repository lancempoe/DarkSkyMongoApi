package com.lancep.controller.validation;

import com.lancep.airport.errorhandling.WeatherException;

import javax.ws.rs.core.Response;
import java.time.Instant;
import java.util.Arrays;

public class WeatherValidations {

    private static final String INVALID_DATE_ORDER = "Dates must be in order of fromDate/toDate";
    private static final String INVALID_DATE = "Invalid dates param. Unable to parse future dates";

    public static void hasValidDateParams(Long ... dates) {
        final Long[] priorDate = {0L};
        Arrays.stream(dates).forEach(date -> hasValidDateParam(priorDate, date));
    }

    private static void hasValidDateParam(Long[] priorDate, Long date) {
            validateDate(date, priorDate[0]);
            priorDate[0] = date;
    }

    private static void validateDate(Long date, Long priorDate) {
        boolean isPastDate = date < Instant.now().getEpochSecond();
        boolean isInCorrectOrder = date > priorDate;

        hasExpectedState(isPastDate, INVALID_DATE);
        hasExpectedState(isInCorrectOrder, INVALID_DATE_ORDER);
    }

    private static void hasExpectedState(boolean isExpectedState, String invalidDateOrder) {
        if (!isExpectedState) {
            invalidDate(invalidDateOrder);
        }
    }

    private static void invalidDate(String message) {
        throw new WeatherException(Response.Status.BAD_REQUEST, message);
    }
}
