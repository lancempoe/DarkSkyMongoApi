package com.lancep;

import com.lancep.controller.HealthResource;
import com.lancep.controller.game.WeatherResource;
import com.lancep.airport.errorhandling.AppExceptionMapper;
import org.glassfish.jersey.server.ResourceConfig;

public class RestApplication extends ResourceConfig {

    public RestApplication() {
        // register application resources
        register(HealthResource.class);
        register(WeatherResource.class);

        // register application provider
        register(AppExceptionMapper.class);
    }
}
