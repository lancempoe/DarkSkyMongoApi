package com.lancep.service.impl;

import com.lancep.airport.errorhandling.WeatherException;
import com.lancep.airport.orm.AirportDailyWeather;
import com.lancep.service.DarkSkyService;
import com.lancep.service.subDarkSky.model.DarkSkyForecast;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.logging.Logger;

@Component
public class DarkSkyServiceImpl implements DarkSkyService {

    private static final Logger logger = Logger.getLogger(DarkSkyForecast.class.getName());
    private static final String HTTPS_API_DARK_SKY_NET_FORECAST = "https://api.darksky.net/forecast";
    private static final String KEY = "PUT_YOUR_KEY_HERE"; //TODO DO NOT COMMIT THIS LINE
    private Client client;

    @PostConstruct
    protected void init() {
        client = ClientBuilder.newClient();
    }

    public AirportDailyWeather getDailyWeather(String geoLocation, Long epochMilli) {
        DarkSkyForecast darkSkyForecast;
        try {
            String url = String.format("%s/%s/%s,%s", HTTPS_API_DARK_SKY_NET_FORECAST, KEY, geoLocation, epochMilli);
            URI uri = new URI(url);
            WebTarget target = client
                    .target(uri)
                    .queryParam("exclude", "currently,minutely,hourly,alerts,flags");
            darkSkyForecast = target.request(MediaType.APPLICATION_JSON).get(DarkSkyForecast.class);
        } catch (Exception e) {
            logger.warning(String.format("Failed to contact DarkSky: %s",e.getMessage()));
            throw new WeatherException(Response.Status.REQUEST_TIMEOUT);
        }
        return getAirportDailyWeather(epochMilli, darkSkyForecast);
    }

    private AirportDailyWeather getAirportDailyWeather(Long epochMilli, DarkSkyForecast darkSkyForecast) {
        AirportDailyWeather airportDailyWeather = new AirportDailyWeather();
        airportDailyWeather.setId(epochMilli);
        airportDailyWeather.setDarkSkyForecast(darkSkyForecast);
        return airportDailyWeather;
    }
}
