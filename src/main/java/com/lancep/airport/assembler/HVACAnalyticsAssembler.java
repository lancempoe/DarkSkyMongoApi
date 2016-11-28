package com.lancep.airport.assembler;

import com.lancep.airport.client.HVACAnalytics;
import com.lancep.airport.orm.AirportDailyWeather;

import java.util.logging.Logger;

public class HVACAnalyticsAssembler {

    private static final Logger logger = Logger.getLogger(HVACAnalyticsAssembler.class.getName());
    private static final int DEGREE_AC_TURNS_ON = 75;
    private static final int DEGREE_HEAT_TURNS_ON = 62;

    public static HVACAnalytics toClient(AirportDailyWeather airportDailyWeather) {
        if (airportDailyWeather == null) {
            return null;
        }

        HVACAnalytics hvacAnalytics = new HVACAnalytics();
        hvacAnalytics.setDay(airportDailyWeather.getId());
        hvacAnalytics.setCoolingUsed(isCoolingUsed(airportDailyWeather));
        hvacAnalytics.setHeatingUsed(isHeatingUsed(airportDailyWeather));
        return hvacAnalytics;
    }

    private static boolean isHeatingUsed(AirportDailyWeather airportDailyWeather) {
        boolean isHeadingUsed = false;
        try {
            isHeadingUsed = airportDailyWeather
                    .getDarkSkyForecast()
                    .getDaily()
                    .getData()
                    .get(0)
                    .getTemperatureMin() < DEGREE_HEAT_TURNS_ON;
        } catch (Exception e) {
            logger.warning(String.format("Unable to find TemperatureMin for id: %s", airportDailyWeather.getId()));
        }
        return isHeadingUsed;
    }

    private static boolean isCoolingUsed(AirportDailyWeather airportDailyWeather) {
        boolean isCoolingUsed = false;
        try {
            isCoolingUsed = airportDailyWeather
                    .getDarkSkyForecast()
                    .getDaily()
                    .getData()
                    .get(0)
                    .getTemperatureMax() > DEGREE_AC_TURNS_ON;
        } catch (Exception e) {
            logger.warning(String.format("Unable to find TemperatureMax for id: %s", airportDailyWeather.getId()));
        }
        return isCoolingUsed;
    }
}
