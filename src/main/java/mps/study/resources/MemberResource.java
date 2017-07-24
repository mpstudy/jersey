package mps.study.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import mps.study.core.MemberService;
import mps.study.utils.TaskExecutor;
import mps.study.vo.Member;
import org.glassfish.jersey.server.ManagedAsync;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;
import static javax.ws.rs.core.Response.Status.OK;
import static javax.ws.rs.core.Response.Status.SERVICE_UNAVAILABLE;

@Path("member")
public class MemberResource {
    private static final Logger LOGGER = Logger.getLogger(MemberResource.class.getName());
    private static final Level DEBUG = Level.INFO;

    @Inject
    private TaskExecutor executor;

    @Inject
    private MemberService memberService;

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ManagedAsync
    public void find(@PathParam("id") String id, @Suspended final AsyncResponse asyncResponse) {
        LOGGER.log(DEBUG, "find!");

        CompletableFuture.supplyAsync(() -> {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return memberService.find(id);
                }, executor)
                .thenApply(result -> asyncResponse.resume(Response.status(OK).entity(result).build()))
                .exceptionally(e -> asyncResponse.resume(Response.status(INTERNAL_SERVER_ERROR).entity(e).build()));

        asyncResponse.setTimeout(3000, TimeUnit.MILLISECONDS);
        asyncResponse.setTimeoutHandler(ar -> ar.resume(Response.status(SERVICE_UNAVAILABLE).entity("Operation timed out").build()));

        LOGGER.log(DEBUG, "end!");
    }

    /* singUp(POST /member) */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ManagedAsync
    public void signUp(String json, @Suspended final AsyncResponse asyncResponse) throws IOException {
        LOGGER.log(DEBUG, "signUp");

        CompletableFuture.supplyAsync(() -> memberService.add(json), executor)
                .thenApply(result -> asyncResponse.resume(Response.status(OK).entity(result).build()))
                .exceptionally(e -> asyncResponse.resume(Response.status(INTERNAL_SERVER_ERROR).entity(e).build()));

        asyncResponse.setTimeout(3000, TimeUnit.MILLISECONDS);
        asyncResponse.setTimeoutHandler(ar -> ar.resume(Response.status(SERVICE_UNAVAILABLE).entity("Operation timed out").build()));

        LOGGER.log(DEBUG, "end");
    }

    /* change pw (PUT /member/{id}) */

    /* withdraw(DELETE /member/{id}) */

    /* signIn(POST /member/login) */

}
