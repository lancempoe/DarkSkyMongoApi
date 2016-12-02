package com.lancep.service;

import com.lancep.airport.errorhandling.WeatherException;
import com.lancep.airport.orm.AirportDailyWeather;
import com.lancep.service.impl.DarkSkyServiceImpl;
import com.lancep.service.subDarkSky.model.DarkSkyForecast;
import mockit.*;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import java.net.URI;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@SuppressWarnings("ConstantConditions")
public class DarkSkyServiceTest {

    private @Tested DarkSkyServiceImpl subject;
    private @Injectable Client client;
    private @Injectable WebTarget webTarget;
    private @Injectable Builder builder;

    private DarkSkyForecast darkSkyForecast = new DarkSkyForecast();

    @Before
    public void init() {
        new MockUp<ClientBuilder>()
        {
            @Mock
            Client newClient()
            {
                return client;
            }
        };
        new Expectations() {{
            client.target((URI) any); result = webTarget; minTimes = 0;
            webTarget.queryParam((String) any, any); result = webTarget; minTimes = 0;
            webTarget.request((String) any); result = builder; minTimes = 0;
            builder.get(DarkSkyForecast.class); result = darkSkyForecast; minTimes = 0;
        }};
    }


    @Test
    public void canGetDarkSkyForecastFromDailyWeather() {
        long day = 1234567L;
        AirportDailyWeather dailyWeather = subject.getDailyWeather("geoLocation", day);
        assertThat(dailyWeather.getDarkSkyForecast(), is(darkSkyForecast));
    }

    @Test
    public void canGetIdFromDailyWeather() {
        long id = 1234567L;
        AirportDailyWeather dailyWeather = subject.getDailyWeather("geoLocation", id);
        assertThat(dailyWeather.getId(), is(id));
    }

    @Test(expected = WeatherException.class)
    public void handlesWhenDarkSkyIsDown() throws Exception {
        new Expectations() {{
            client.target((URI) any); result = webTarget;
            webTarget.queryParam((String) any, any); result = webTarget;
            webTarget.request((String) any); result = builder;
            builder.get(DarkSkyForecast.class); result = new Exception();
        }};

        subject.getDailyWeather("geoLocation", 1234567L);
    }

}