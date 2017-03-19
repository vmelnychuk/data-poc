package io.fourfinance.pos.dataprovider.service;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/ping")
public class PingResource {

    private static final String PING_MESSAGE = "OK";

    @GET
    @Produces("text/plain")
    public String ping() {
        return PING_MESSAGE;
    }
}
