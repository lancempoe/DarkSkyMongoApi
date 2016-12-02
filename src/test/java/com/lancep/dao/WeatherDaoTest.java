package com.lancep.dao;

import com.lancep.airport.errorhandling.WeatherException;
import com.lancep.airport.orm.AirportDailyWeather;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.Verifications;
import org.junit.Test;
import org.springframework.data.mapping.model.MappingException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

@SuppressWarnings("CanBeFinal")
public class WeatherDaoTest {

    private @Tested WeatherDaoImpl subject;
    private @Injectable MongoTemplate mongoTemplate;
    private static final Long DAY = 1480147200L;
    private static final List<Long> DAYS = Arrays.asList(1480147200L, 123456L);
    private final AirportDailyWeather DAILY_WEATHER = new AirportDailyWeather();
    private final List<AirportDailyWeather> DAILY_WEATHER_LIST = new ArrayList<>();

    @Test
    public void save() throws Exception {
        subject.save(DAILY_WEATHER);
        new Verifications() {{
            mongoTemplate.save(DAILY_WEATHER);
        }};
    }

    @Test(expected = WeatherException.class)
    public void saveCanHandleDbErrors() {
        new Expectations() {{
            mongoTemplate.save(DAILY_WEATHER); result = new Exception();
        }};
        subject.save(DAILY_WEATHER);
    }

    @Test
    public void canFindById() throws Exception {
        new Expectations() {{
            mongoTemplate.findById(DAY, AirportDailyWeather.class); result = DAILY_WEATHER;
        }};
        assertThat(subject.findById(DAY), is(DAILY_WEATHER));
    }

    @Test
    public void findByIdHandlesInvalidId() throws Exception {
        new Expectations() {{
            mongoTemplate.findById(DAY, AirportDailyWeather.class); result = new MappingException("");
        }};
        assertThat(subject.findById(DAY), is(nullValue()));
    }

    @Test(expected = WeatherException.class)
    public void findByIdHandlesDBErrors() throws Exception {
        new Expectations() {{
            mongoTemplate.findById(DAY, AirportDailyWeather.class); result = new Exception();
        }};
        subject.findById(DAY);
    }

    @Test
    public void findByIds() throws Exception {
        new Expectations() {{
            mongoTemplate.find((Query)any, AirportDailyWeather.class); result = DAILY_WEATHER_LIST;
        }};
        assertThat(subject.findByIds(DAYS), is(DAILY_WEATHER_LIST));
    }

    @Test(expected = WeatherException.class)
    public void findByIdsHandlesDbErrors() {
        new Expectations() {{
            mongoTemplate.find((Query)any, AirportDailyWeather.class); result = new Exception();
        }};
        subject.findByIds(DAYS);
    }

}