package com.lancep.controller.validation;

import com.lancep.airport.errorhandling.WeatherException;
import org.junit.Test;

import java.time.*;

public class WeatherValidationsTest {

    private static final ZonedDateTime NOW = ZonedDateTime.ofInstant(Instant.now(), ZoneId.systemDefault());

    @Test
    public void handlesValidDates() {
        WeatherValidations.hasValidDateParams(getDaysAgoAsEpoch(2),getDaysAgoAsEpoch(1));
    }

    @Test(expected = WeatherException.class)
    public void failedWhenDatesAreOutOfOrder() {
        WeatherValidations.hasValidDateParams(getDaysAgoAsEpoch(1),getDaysAgoAsEpoch(2));
    }

    @Test(expected = WeatherException.class)
    public void failedWhenDatesAreTheSame() {
        WeatherValidations.hasValidDateParams(getDaysAgoAsEpoch(1),getDaysAgoAsEpoch(1));
    }

    private long getDaysAgoAsEpoch(int days) {
        return NOW.minusDays(days).toEpochSecond();
    }
}