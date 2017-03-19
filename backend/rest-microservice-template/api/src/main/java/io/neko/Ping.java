package io.neko;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/ping")
public class PingResource {

    private static final String PING_MESSAGE = "OK";

    @GET
    @Path("/")
    @Produces("text/plain")
    public String ping() {
        return PING_MESSAGE;
    }
}