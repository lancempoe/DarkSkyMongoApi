package com.lancep.airport.errorhandling;

import org.junit.Test;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class AppExceptionMapperTest {

    private final AppExceptionMapper subject = new AppExceptionMapper();

    @Test
    public void willMapWarExceptionStatusToResponse() throws Exception {
        WeatherException exception = new WeatherException(Status.BAD_GATEWAY);
        assertThat(subject.toResponse(exception).getStatusInfo(), is(Status.BAD_GATEWAY));
    }

    @Test
    public void willMapWarExceptionMessageToResponse() throws Exception {
        String message = "Taran";
        WeatherException exception = new WeatherException(Status.BAD_GATEWAY, message);
        assertThat(subject.toResponse(exception).getEntity(), is(message));
    }

    @Test
    public void willReturnJsonMediaType() throws Exception {
        WeatherException exception = new WeatherException(Status.BAD_GATEWAY, null);
        assertThat(subject.toResponse(exception).getMediaType(), is(MediaType.APPLICATION_JSON_TYPE));
    }

}