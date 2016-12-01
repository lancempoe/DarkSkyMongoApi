package com.lancep.util;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class DateUtil {

    public static List<Long> getStartOfDaysFromDates(Long startDate, Long endDate) {
        List<Long> dates = new ArrayList<>();
        ZonedDateTime fromDate = getStartOfDay(startDate);
        ZonedDateTime toDate = getNullableValueZonedDateTime(endDate);
        while(!fromDate.isAfter(toDate)) {
            dates.add(fromDate.toEpochSecond());
            fromDate = fromDate.plusDays(1);
        }
        return dates;
    }

    private static ZonedDateTime getStartOfDay(Long date) {
        ZonedDateTime zonedDate = ZonedDateTime.ofInstant( Instant.ofEpochSecond(date), ZoneId.systemDefault());
        return zonedDate.truncatedTo(ChronoUnit.DAYS);
    }

    /**
     * You may be requesting only 1 day of data
     */
    private static ZonedDateTime getNullableValueZonedDateTime(Long endDate) {
        return endDate != null ? getStartOfDay(endDate) : ZonedDateTime.now().truncatedTo(ChronoUnit.DAYS);
    }
}
