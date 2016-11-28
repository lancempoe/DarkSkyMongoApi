package com.lancep.controller;

import org.junit.Test;

import javax.ws.rs.core.Response;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class HealthResourceTest {

    HealthResource subject = new HealthResource();

    @Test
    public void amIAwakeReturnsOk() throws Exception {
        assertThat(subject.amIAwake().getStatusInfo(), is(Response.Status.OK));
    }

}