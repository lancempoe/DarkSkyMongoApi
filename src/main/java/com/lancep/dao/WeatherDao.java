package com.lancep.dao;

import com.lancep.airport.orm.AirportDailyWeather;

import java.util.List;

public interface WeatherDao {

    void save(AirportDailyWeather dailyWeather);

    AirportDailyWeather findById(Long timeKey);

    List<AirportDailyWeather> findByIds(List<Long> timeKeys);

}
