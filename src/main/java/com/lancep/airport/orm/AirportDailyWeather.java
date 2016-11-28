package com.lancep.airport.orm;

import com.lancep.config.MongoProperties;
import com.lancep.service.subDarkSky.model.DarkSkyForecast;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = MongoProperties.AIRPORT_DB)
public class AirportDailyWeather {

    @Id
    private long id;
    private DarkSkyForecast darkSkyForecast;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public DarkSkyForecast getDarkSkyForecast() {
        return darkSkyForecast;
    }

    public void setDarkSkyForecast(DarkSkyForecast darkSkyForecast) {
        this.darkSkyForecast = darkSkyForecast;
    }
}
