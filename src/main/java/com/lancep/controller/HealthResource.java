package com.lancep.controller;

import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Component
@Path("health")
public class HealthResource {

    @GET
    public Response amIAwake() {
        return Response.ok("Portland's Dark Sky Analyzer is up and ready.", MediaType.TEXT_PLAIN).build();
    }

}
