package com.lancep.service;

import com.lancep.airport.client.HVACAnalytics;

import java.util.List;

public interface WeatherService {

    List<HVACAnalytics> getAirportHVACAnalytics(Long startDates, Long endDate);

}
