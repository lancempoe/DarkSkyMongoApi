package com.lancep.util;

import org.junit.Test;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class DateUtilTest {

    private static final ZonedDateTime NOW = ZonedDateTime.ofInstant(Instant.now(), ZoneId.systemDefault());

    @Test
    public void getStartOfDaysFromDates() throws Exception {
        ZonedDateTime startLocal = NOW.minusDays(2);
        ZonedDateTime endLocal = NOW.minusDays(1);
        long start = getAsLong(startLocal);
        long end = getAsLong(endLocal);

        List<Long> cleanedDates = DateUtil.getStartOfDaysFromDates(start, end);

        assertThat(cleanedDates.get(0), is(getAsLong(startLocal.truncatedTo(ChronoUnit.DAYS))));
        assertThat(cleanedDates.get(1), is(getAsLong(endLocal.truncatedTo(ChronoUnit.DAYS))));
    }

    @Test
    public void canGetStartOfARangeOfDates() {
        ZonedDateTime startLocal = NOW.minusDays(4);
        ZonedDateTime endLocal = NOW.minusDays(1);
        long start = getAsLong(startLocal);
        long end = getAsLong(endLocal);

        List<Long> cleanedDates = DateUtil.getStartOfDaysFromDates(start, end);

        assertThat(cleanedDates.size(), is(4));
    }

    private long getAsLong(ZonedDateTime zonedDateTime) {
        return zonedDateTime.toEpochSecond();
    }

}