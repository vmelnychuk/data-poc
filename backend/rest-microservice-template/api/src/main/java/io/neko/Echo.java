package io.neko;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/echo")
public class PingResource {

    private static final String PING_MESSAGE = "OK";

    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public String echo() {
        return PING_MESSAGE;
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    List<ApplicationDetails> getApplications(List<String> applicationIds);

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    ApplicationDetails get(@PathParam("id") String id);

    @GET
    @Path("/{id}")
    @Produces(MediaType.TEXT_HTML)
    String getApplicationPresentation(@PathParam("id") String id);
}

