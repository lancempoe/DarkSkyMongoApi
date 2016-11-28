package com.lancep.service;


import com.lancep.airport.client.HVACAnalytics;
import com.lancep.airport.errorhandling.WeatherException;
import com.lancep.airport.orm.AirportDailyWeather;
import com.lancep.airport.assembler.HVACAnalyticsAssembler;
import com.lancep.config.MongoDBConfig;
import com.lancep.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mapping.model.MappingException;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.Response.Status;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Component
public class WeatherService {

    private static final Logger logger = Logger.getLogger( WeatherService.class.getName() );
    private static final String AIRPORT_GEO_LOCATION = "45.5898,-122.5951";

    @Autowired
    private MongoDBConfig mongoConfig;
    @Autowired
    private DarkSkyService darkSkyService;


    public List<HVACAnalytics> getAirportHVACAnalytics(Long startDates, Long endDate) {
        List<HVACAnalytics> hvacAnalyticsList = new ArrayList<>();
        List<Long> cleanedDates = DateUtil.getStartOfDaysFromDates(startDates, endDate);
        cleanedDates.stream().forEach(date -> {
            HVACAnalytics hvacAnalytics = getAirportHVACAnalytics(date);
            hvacAnalyticsList.add(hvacAnalytics);
        });

        return hvacAnalyticsList;
    }

    private HVACAnalytics getAirportHVACAnalytics(Long date) {
        AirportDailyWeather dailyWeather = getSavedAirportDailyWeather(date);
        if (dailyWeather == null) {
            dailyWeather = darkSkyService.getDailyWeather(AIRPORT_GEO_LOCATION, date);
            saveAirportDailyWeather(dailyWeather);
        }
        return HVACAnalyticsAssembler.toClient(dailyWeather);
    }

    private void saveAirportDailyWeather(AirportDailyWeather dailyWeather) {
        try {
            MongoOperations mongoOperation = mongoConfig.mongoTemplate();
            mongoOperation.save(dailyWeather);
            logger.info(String.format("New AirportDailyWeather Created: %s", dailyWeather.getId()));
        } catch (Exception e) {
            logger.warning(String.format("Failed to save AirportDailyWeather: %d, %s", dailyWeather.getId(), e));
            throw new WeatherException(Status.GATEWAY_TIMEOUT);
        }
    }

    private AirportDailyWeather getSavedAirportDailyWeather(Long timeKey) {
        AirportDailyWeather data = null;
        try {
            MongoOperations mongoOperation = mongoConfig.mongoTemplate();
            data = mongoOperation.findById(timeKey, AirportDailyWeather.class);
            logger.info(String.format("Existing AirportDailyWeather Reused: %s", timeKey));
        } catch (MappingException e) {
            logger.info(String.format("AirportDailyWeather with key of %d not found", timeKey));
        } catch (Exception e) {
            logger.warning(String.format("Failed to get AirportDailyWeather: %d, %s", timeKey, e));
            throw new WeatherException(Status.GATEWAY_TIMEOUT);
        }
        return data;
    }
}
