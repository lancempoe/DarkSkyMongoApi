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
    private Long date = 123l;
    private Daily daily = new Daily();
    private List<Datum_> data = new ArrayList<>();
    private Datum_ datum = new Datum_();
    private Double HOT_DAY = new Double(76);
    private Double NOT_HOT_DAY = new Double(75);
    private Double NOT_COLD_DAY = new Double(62);
    private Double COLD_DAY = new Double(61);

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
        DarkSkyForecast darkSkyForecast = buildWeatherDay(null, HOT_DAY);
        dailyWeather.setDarkSkyForecast(darkSkyForecast);
        assertThat(HVACAnalyticsAssembler.toClient(dailyWeather).isCoolingUsed(), is(true));
    }

    @Test
    public void willNotSetCoolingUsedOnCoolDays() {
        DarkSkyForecast darkSkyForecast = buildWeatherDay(null, NOT_HOT_DAY);
        dailyWeather.setDarkSkyForecast(darkSkyForecast);
        assertThat(HVACAnalyticsAssembler.toClient(dailyWeather).isCoolingUsed(), is(false));
    }

    @Test
    public void willSetHeatingUsedOnCoolDays() {
        DarkSkyForecast darkSkyForecast = buildWeatherDay(COLD_DAY, null);
        dailyWeather.setDarkSkyForecast(darkSkyForecast);
        assertThat(HVACAnalyticsAssembler.toClient(dailyWeather).isHeatingUsed(), is(true));
    }

    @Test
    public void willNotSetHeatingUsedOnCoolDays() {
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