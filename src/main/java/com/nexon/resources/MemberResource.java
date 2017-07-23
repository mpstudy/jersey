package com.nexon.resources;

import com.nexon.services.MemberService;
import com.nexon.utils.TaskExecutor;
import com.nexon.vo.Member;
import org.glassfish.jersey.server.ManagedAsync;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;
import static javax.ws.rs.core.Response.Status.SERVICE_UNAVAILABLE;

public class MemberResource {
    private static final Logger LOGGER = Logger.getLogger(MemberService.class.getName());
    private static final Level DEBUG = Level.INFO;

    @Inject
    TaskExecutor executor;

    /* singUp(POST /member) */
    @POST
    @Path("member")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void signUp(Member member, @Suspended final AsyncResponse asyncResponse) {
        LOGGER.log(DEBUG, member.toString());
        CompletableFuture.supplyAsync(() -> MemberService.register(member), executor)
                .thenApply(status -> asyncResponse.resume(Response.status(status)))
                .exceptionally(e -> asyncResponse.resume(Response.status(INTERNAL_SERVER_ERROR).entity(e).build()));

        asyncResponse.setTimeout(1000, TimeUnit.MILLISECONDS);
        asyncResponse.setTimeoutHandler(ar -> ar.resume(Response.status(SERVICE_UNAVAILABLE).entity("Operation timed out").build()));
        LOGGER.log(DEBUG, "end");
    }

    /* change pw (POST /member/{id}) */

    /* withdraw(DELETE /member/{id}) */

    /* signIn(POST /member/login) */


}
