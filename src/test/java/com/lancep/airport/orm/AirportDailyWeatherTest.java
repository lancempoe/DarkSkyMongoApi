package com.lancep.airport.orm;

import com.lancep.service.subDarkSky.model.DarkSkyForecast;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class AirportDailyWeatherTest {

    AirportDailyWeather subject = new AirportDailyWeather();

    @Test
    public void canGetId() {
        long id = 123l;
        subject.setId(id);
        assertThat(subject.getId(), is(id));
    }

    @Test
    public void canGetDarkSkyForecast() {
        DarkSkyForecast dark = new DarkSkyForecast();
        subject.setDarkSkyForecast(dark);
        assertThat(subject.getDarkSkyForecast(), is(dark));
    }
}