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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class WeatherDaoTest {

    private @Tested WeatherDaoImpl subject;
    private @Injectable MongoTemplate mongoTemplate;
    private static final Long DAY = 1480147200L;
    private final AirportDailyWeather DAILY_WEATHER = new AirportDailyWeather();

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

    }

}