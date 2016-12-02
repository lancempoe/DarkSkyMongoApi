package com.lancep.airport.assembler;

import com.lancep.airport.orm.AirportDailyWeather;
import com.lancep.service.subDarkSky.model.Daily;
import com.lancep.service.subDarkSky.model.DarkSkyForecast;
import com.lancep.service.subDarkSky.model.Datum_;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class HVACAnalyticsAssemblerTest {

    private AirportDailyWeather dailyWeather;
    private final Long date = 123L;
    private final Daily daily = new Daily();
    private final List<Datum_> data = new ArrayList<>();
    private final Datum_ datum = new Datum_();

    @Before
    public void init() {
        dailyWeather = new AirportDailyWeather();
        dailyWeather.setId(date);
    }
    @Test
    public void willAssembleDay() throws Exception {
        assertThat(HVACAnalyticsAssembler.toClient(dailyWeather).getDay(), is(date));
    }

    @Test
    public void willSetCoolingUsedOnCoolDays() {
        Double HOT_DAY = 76d;
        DarkSkyForecast darkSkyForecast = buildWeatherDay(null, HOT_DAY);
        dailyWeather.setDarkSkyForecast(darkSkyForecast);
        assertThat(HVACAnalyticsAssembler.toClient(dailyWeather).isCoolingUsed(), is(true));
    }

    @Test
    public void willNotSetCoolingUsedOnCoolDays() {
        Double NOT_HOT_DAY = 75d;
        DarkSkyForecast darkSkyForecast = buildWeatherDay(null, NOT_HOT_DAY);
        dailyWeather.setDarkSkyForecast(darkSkyForecast);
        assertThat(HVACAnalyticsAssembler.toClient(dailyWeather).isCoolingUsed(), is(false));
    }

    @Test
    public void willSetHeatingUsedOnCoolDays() {
        Double COLD_DAY = 61d;
        DarkSkyForecast darkSkyForecast = buildWeatherDay(COLD_DAY, null);
        dailyWeather.setDarkSkyForecast(darkSkyForecast);
        assertThat(HVACAnalyticsAssembler.toClient(dailyWeather).isHeatingUsed(), is(true));
    }

    @Test
    public void willNotSetHeatingUsedOnCoolDays() {
        Double NOT_COLD_DAY = 62d;
        DarkSkyForecast darkSkyForecast = buildWeatherDay(NOT_COLD_DAY, null);
        dailyWeather.setDarkSkyForecast(darkSkyForecast);
        assertThat(HVACAnalyticsAssembler.toClient(dailyWeather).isHeatingUsed(), is(false));
    }

    private DarkSkyForecast buildWeatherDay(Double min, Double max) {
        DarkSkyForecast darkSkyForecast = new DarkSkyForecast();
        darkSkyForecast.setDaily(daily);
        daily.setData(data);
        data.add(datum);
        datum.setTemperatureMin(min);
        datum.setTemperatureMax(max);
        return darkSkyForecast;
    }



}