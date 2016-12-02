package com.lancep.airport.errorhandling;


import javax.ws.rs.core.Response.Status;

public class WeatherException extends RuntimeException {

    private final Status status;

    public WeatherException(Status status) {
        this(status, null);
    }

    public WeatherException(Status status, String message) {
        super(message);
        this.status  = status;
    }

    public Status getStatus() {
        return status;
    }
}
