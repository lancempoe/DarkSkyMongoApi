package com.lancep.service;

import com.lancep.airport.client.HVACAnalytics;
import com.lancep.airport.errorhandling.WeatherException;
import com.lancep.airport.orm.AirportDailyWeather;
import com.lancep.config.MongoDBConfig;
import com.lancep.service.impl.WeatherServiceImpl;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.mapping.model.MappingException;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class WeatherServiceTest {

    private @Tested WeatherServiceImpl subject;
    private @Injectable DarkSkyService darkSkyService;
    private @Injectable MongoDBConfig mongoDBConfig;
    private @Injectable MongoTemplate mongoOperation;

    private AirportDailyWeather savedDailyWeather1 = new AirportDailyWeather();
    private AirportDailyWeather savedDailyWeather2 = new AirportDailyWeather();
    private AirportDailyWeather newDailyWeather1 = new AirportDailyWeather();
    private AirportDailyWeather newDailyWeather2 = new AirportDailyWeather();
    private static final Long START_DATE = 1480147200l;
    private static final Long END_DATE = 1480233600l;


    @Before
    public void init() throws Exception {
        savedDailyWeather1.setId(START_DATE);
        savedDailyWeather2.setId(END_DATE);
        newDailyWeather1.setId(START_DATE);
        newDailyWeather2.setId(END_DATE);

        new Expectations() {{
            mongoDBConfig.mongoTemplate(); result = mongoOperation; minTimes = 0;
        }};
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
    public void handlesWhenMongoIsDown() {
        new Expectations() {{
            mongoOperation.findById(START_DATE, AirportDailyWeather.class); result = new Exception(); minTimes = 1;
        }};
        subject.getAirportHVACAnalytics(START_DATE, END_DATE);
    }

    @Test(expected = WeatherException.class)
    public void handlesWhenMongoIsDownWhenSaving() {
        new Expectations() {{
            mongoOperation.findById(START_DATE, AirportDailyWeather.class); result = new MappingException(""); minTimes =1;
            darkSkyService.getDailyWeather(anyString, START_DATE); result = newDailyWeather1; minTimes = 1;
            mongoOperation.save(newDailyWeather1); result = new Exception(); minTimes = 1;
        }};
        subject.getAirportHVACAnalytics(START_DATE, END_DATE);
    }

    private void setExpectationsWhenDatesInDB() {
        new Expectations() {{
            mongoOperation.findById(START_DATE, AirportDailyWeather.class); result = savedDailyWeather1; minTimes = 1;
            mongoOperation.findById(END_DATE, AirportDailyWeather.class); result = savedDailyWeather2; minTimes = 1;
            darkSkyService.getDailyWeather(anyString, START_DATE); result = newDailyWeather1; maxTimes = 0;
            darkSkyService.getDailyWeather(anyString, END_DATE); result = newDailyWeather2; maxTimes = 0;
        }};
    }

    private void setExpectationsWhenDataNotInDB() {
        new Expectations() {{
            mongoOperation.findById(START_DATE, AirportDailyWeather.class); result = new MappingException("");
            mongoOperation.findById(END_DATE, AirportDailyWeather.class); result = new MappingException("");
            darkSkyService.getDailyWeather(anyString, START_DATE); result = newDailyWeather1; minTimes = 1;
            darkSkyService.getDailyWeather(anyString, END_DATE); result = newDailyWeather2; minTimes = 1;
        }};
    }
}