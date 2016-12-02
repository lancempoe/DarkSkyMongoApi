package com.lancep.controller;

import com.lancep.airport.client.HVACAnalytics;
import com.lancep.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import static com.lancep.controller.validation.WeatherValidations.hasValidDateParams;

@Controller
@Path("weather")
public class WeatherResource {

    private WeatherService weatherService;

    @POST
    @Path("airport")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAirportAnalytics(
                                  @QueryParam("start_date") Long startDate,
                                  @QueryParam("end_date") Long endDate) {
        hasValidDateParams(startDate, endDate);
        List<HVACAnalytics> analytics = weatherService.getAirportHVACAnalytics(startDate, endDate);
        return Response.ok(analytics)
                       .type(MediaType.APPLICATION_JSON)
                       .build();
    }

    @Autowired
    public void setWeatherService(WeatherService weatherService) {
        this.weatherService = weatherService;
    }
}
