package com.lancep.service.impl;

import com.lancep.airport.assembler.HVACAnalyticsAssembler;
import com.lancep.airport.client.HVACAnalytics;
import com.lancep.airport.orm.AirportDailyWeather;
import com.lancep.dao.WeatherDao;
import com.lancep.service.DarkSkyService;
import com.lancep.service.WeatherService;
import com.lancep.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class WeatherServiceImpl implements WeatherService {

    private static final String AIRPORT_GEO_LOCATION = "45.5898,-122.5951";

    private WeatherDao weatherDao;
    private DarkSkyService darkSkyService;

    public List<HVACAnalytics> getAirportHVACAnalytics(Long startDates, Long endDate) {
        List<HVACAnalytics> hvacAnalyticsList = new ArrayList<>();
        List<Long> cleanedDates = DateUtil.getStartOfDaysFromDates(startDates, endDate);
        cleanedDates.forEach(date -> {
            HVACAnalytics hvacAnalytics = getAirportHVACAnalytics(date);
            hvacAnalyticsList.add(hvacAnalytics);
        });

        return hvacAnalyticsList;
    }

    private HVACAnalytics getAirportHVACAnalytics(Long date) {
        AirportDailyWeather dailyWeather = weatherDao.findById(date);
        if (dailyWeather == null) {
            dailyWeather = darkSkyService.getDailyWeather(AIRPORT_GEO_LOCATION, date);
            saveAirportDailyWeather(dailyWeather);
        }
        return HVACAnalyticsAssembler.toClient(dailyWeather);
    }

    private void saveAirportDailyWeather(AirportDailyWeather dailyWeather) {
        weatherDao.save(dailyWeather);
    }

    @Autowired
    public void setWeatherDao(WeatherDao weatherDao) {
        this.weatherDao = weatherDao;
    }

    @Autowired
    public void setDarkSkyService(DarkSkyService darkSkyService) {
        this.darkSkyService = darkSkyService;
    }
}
