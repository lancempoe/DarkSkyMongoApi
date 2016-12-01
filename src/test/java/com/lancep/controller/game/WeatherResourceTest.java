package com.lancep.controller.game;

import com.lancep.airport.client.HVACAnalytics;
import com.lancep.controller.WeatherResource;
import com.lancep.service.WeatherService;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import org.junit.Test;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.*;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class WeatherResourceTest {

    @Tested
    private WeatherResource subject;
    @Injectable
    private WeatherService weatherService;

    private static final ZonedDateTime NOW = ZonedDateTime.ofInstant(Instant.now(), ZoneId.systemDefault());
    private static final Long START_DATE = NOW.minusDays(2).toEpochSecond();
    private static final Long END_DATE = NOW.minusDays(1).toEpochSecond();
    private List<HVACAnalytics> results = new ArrayList<>();

    @Test
    public void getAirportAnalyticsRespondsOk() throws Exception {
        assertThat(subject.getAirportAnalytics(START_DATE, END_DATE).getStatusInfo(), is(Response.Status.OK));
    }

    @Test
    public void getAirportAnalyticsRespondsWithWarGames() throws Exception {
        new Expectations() {{
            weatherService.getAirportHVACAnalytics(START_DATE, END_DATE); result = results;
        }};
        assertThat(subject.getAirportAnalytics(START_DATE, END_DATE).getEntity(), is(results));
    }

    @Test
    public void getAirportAnalyticsRespondsWithTypeJson() throws Exception {
        assertThat(subject.getAirportAnalytics(START_DATE, END_DATE).getMediaType(), is(MediaType.APPLICATION_JSON_TYPE));
    }


}