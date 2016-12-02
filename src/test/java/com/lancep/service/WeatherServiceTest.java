package com.lancep.service;

import com.lancep.airport.client.HVACAnalytics;
import com.lancep.airport.errorhandling.WeatherException;
import com.lancep.airport.orm.AirportDailyWeather;
import com.lancep.dao.WeatherDao;
import com.lancep.service.impl.WeatherServiceImpl;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.Response;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class WeatherServiceTest {

    private @Tested WeatherServiceImpl subject;
    private @Injectable DarkSkyService darkSkyService;
    private @Injectable WeatherDao weatherDao;

    private final AirportDailyWeather savedDailyWeather1 = new AirportDailyWeather();
    private final AirportDailyWeather savedDailyWeather2 = new AirportDailyWeather();
    private final AirportDailyWeather newDailyWeather1 = new AirportDailyWeather();
    private final AirportDailyWeather newDailyWeather2 = new AirportDailyWeather();
    private static final Long START_DATE = 1480147200L;
    private static final Long END_DATE = 1480233600L;

    @Before
    public void init() throws Exception {
        savedDailyWeather1.setId(START_DATE);
        savedDailyWeather2.setId(END_DATE);
        newDailyWeather1.setId(START_DATE);
        newDailyWeather2.setId(END_DATE);
    }

    @Test
    public void getCorrectCountWhenBothDatesAlreadySaved() {
        setExpectationsWhenDatesInDB();
        List<HVACAnalytics> results = subject.getAirportHVACAnalytics(START_DATE, END_DATE);
        assertThat(results.size(), is(2));
    }

    @Test
    public void getResultsFromBothDatesAirportFromSavedData() {
        setExpectationsWhenDatesInDB();
        List<HVACAnalytics> results = subject.getAirportHVACAnalytics(START_DATE, END_DATE);
        assertThat(results.get(0).getDay(), is(START_DATE));
        assertThat(results.get(1).getDay(), is(END_DATE));
    }

    @Test
    public void getCorrectCountWhenDatesNotInDB() {
        setExpectationsWhenDataNotInDB();
        List<HVACAnalytics> results = subject.getAirportHVACAnalytics(START_DATE, END_DATE);
        assertThat(results.size(), is(2));
    }

    @Test
    public void getsResultsWhenDatesNotInDB() {
        setExpectationsWhenDataNotInDB();
        List<HVACAnalytics> results = subject.getAirportHVACAnalytics(START_DATE, END_DATE);
        assertThat(results.get(0).getDay(), is(START_DATE));
        assertThat(results.get(1).getDay(), is(END_DATE));
    }

    @Test(expected = WeatherException.class)
    public void handlesWhenMongoIsDownWhenSaving() {
        new Expectations() {{
            weatherDao.findById(START_DATE); result = new WeatherException(Response.Status.BAD_GATEWAY);
        }};
        subject.getAirportHVACAnalytics(START_DATE, END_DATE);
    }

    private void setExpectationsWhenDatesInDB() {
        new Expectations() {{
            weatherDao.findById(START_DATE); result = savedDailyWeather1; minTimes = 1;
            weatherDao.findById(END_DATE); result = savedDailyWeather2; minTimes = 1;
            darkSkyService.getDailyWeather(anyString, START_DATE); result = newDailyWeather1; maxTimes = 0;
            darkSkyService.getDailyWeather(anyString, END_DATE); result = newDailyWeather2; maxTimes = 0;
        }};
    }

    private void setExpectationsWhenDataNotInDB() {
        new Expectations() {{
            weatherDao.findById(START_DATE); result = null;
            weatherDao.findById(END_DATE); result = null;
            darkSkyService.getDailyWeather(anyString, START_DATE); result = newDailyWeather1; minTimes = 1;
            darkSkyService.getDailyWeather(anyString, END_DATE); result = newDailyWeather2; minTimes = 1;
        }};
    }
}