package io.fourfinanceit.dataprovider;

import io.fourfinanceit.dataprovider.dto.ApplicationDetails;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/applications")
public interface ApplicationResource {

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
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
