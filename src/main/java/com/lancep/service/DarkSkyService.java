package com.lancep.service;

import com.lancep.airport.orm.AirportDailyWeather;

public interface DarkSkyService {

    AirportDailyWeather getDailyWeather(String geoLocation, Long epochMilli);

}
