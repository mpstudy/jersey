package mps.study.resources;

import com.avaje.ebean.Ebean;
import mps.study.model.Member;
import mps.study.utils.TaskExecutor;
import org.glassfish.jersey.server.ManagedAsync;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.URI;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static javax.ws.rs.core.Response.Status.*;

@Path("/")
public class CommonResource {
    private static Logger log = LoggerFactory.getLogger(CommonResource.class);

    @Inject
    private TaskExecutor executor;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ManagedAsync
    public void find(@Suspended final AsyncResponse asyncResponse) {
        log.debug("hi!");

        CompletableFuture.supplyAsync(() -> "hello", executor)
                .thenApply(result -> asyncResponse.resume(Response.status(OK).entity(result).build()))
                .exceptionally(e -> asyncResponse.resume(Response.status(INTERNAL_SERVER_ERROR).entity(e).build()));

        asyncResponse.setTimeout(3000, TimeUnit.MILLISECONDS);
        asyncResponse.setTimeoutHandler(ar -> ar.resume(Response.status(SERVICE_UNAVAILABLE).entity("Operation timed out").build()));

        log.debug("end!");
    }
}