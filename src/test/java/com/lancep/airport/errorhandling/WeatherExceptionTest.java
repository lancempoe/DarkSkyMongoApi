package com.lancep.airport.errorhandling;

import org.junit.Test;

import javax.ws.rs.core.Response.Status;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class WeatherExceptionTest {

    @Test
    public void constructorSetsTheStatus() throws Exception {
        WeatherException subject = new WeatherException(Status.FORBIDDEN);
        assertThat(subject.getStatus(), is(Status.FORBIDDEN));
    }

}