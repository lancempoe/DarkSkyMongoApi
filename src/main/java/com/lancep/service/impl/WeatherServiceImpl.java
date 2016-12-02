package com.lancep.service.impl;

import com.lancep.airport.assembler.HVACAnalyticsAssembler;
import com.lancep.airport.client.HVACAnalytics;
import com.lancep.airport.orm.AirportDailyWeather;
import com.lancep.dao.WeatherDao;
import com.lancep.service.DarkSkyService;
import com.lancep.service.WeatherService;
import com.lancep.util.DateUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class WeatherServiceImpl implements WeatherService {

    private static final String AIRPORT_GEO_LOCATION = "45.5898,-122.5951";

    private WeatherDao weatherDao;
    private DarkSkyService darkSkyService;

    public List<HVACAnalytics> getAirportHVACAnalytics(Long startDates, Long endDate) {
        List<Long> cleanedDates = DateUtil.getStartOfDaysFromDates(startDates, endDate);
        return getAirportHVACAnalyticsList(cleanedDates);
    }

    private List<HVACAnalytics> getAirportHVACAnalyticsList(List<Long> dates) {
        List<HVACAnalytics> hvacAnalyticsList = new ArrayList<>();
        Map<Long, AirportDailyWeather> savedDailyWeatherMap = getAirportDailyWeatherMap(dates);

        dates.forEach(date -> {
            HVACAnalytics hvacAnalytics = getHVACAnalytics(savedDailyWeatherMap, date);
            hvacAnalyticsList.add(hvacAnalytics);
        });

        return hvacAnalyticsList;
    }

    private Map<Long, AirportDailyWeather> getAirportDailyWeatherMap(List<Long> dates) {
        List<AirportDailyWeather> dailyWeatherList = weatherDao.findByIds(dates);
        return CollectionUtils.isNotEmpty(dailyWeatherList) ?
                dailyWeatherList
                        .stream()
                        .collect(Collectors
                                .toMap(AirportDailyWeather::getId,
                                       Function.identity())) :
                new HashMap<>();
    }

    private HVACAnalytics getHVACAnalytics(Map<Long, AirportDailyWeather> dailyWeatherMap, Long date) {
        AirportDailyWeather dailyWeather;
        if (dailyWeatherMap.containsKey(date)) {
            dailyWeather = dailyWeatherMap.get(date);
        } else {
            dailyWeather = darkSkyService.getDailyWeather(AIRPORT_GEO_LOCATION, date);
            weatherDao.save(dailyWeather);
        }
        return HVACAnalyticsAssembler.toClient(dailyWeather);
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
