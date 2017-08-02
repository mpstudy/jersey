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

@Path("member")
public class MemberResource {
    private static Logger log = LoggerFactory.getLogger(MemberResource.class);

    @Inject
    private TaskExecutor executor;

    /* find(POST /member)
    * DB 조회
    * 사용자 정보 조회용*/
    @GET
    @Path("{mid}")
    @Produces(MediaType.APPLICATION_JSON)
    @ManagedAsync
    public void find(@PathParam("mid") String mid, @Suspended final AsyncResponse asyncResponse) {
        log.debug("find!");

        CompletableFuture.supplyAsync(() -> Ebean.find(Member.class, mid), executor)
                .thenApply(result -> asyncResponse.resume(Response.status(OK).entity(result).build()))
                .exceptionally(e -> asyncResponse.resume(Response.status(INTERNAL_SERVER_ERROR).entity(e).build()));

        asyncResponse.setTimeout(3000, TimeUnit.MILLISECONDS);
        asyncResponse.setTimeoutHandler(ar -> ar.resume(Response.status(SERVICE_UNAVAILABLE).entity("Operation timed out").build()));

        log.debug("end!");
    }

    /* singUp(POST /member)
    * DB 등록*/
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @ManagedAsync
    public void signUp(Member member, @Suspended final AsyncResponse asyncResponse) throws IOException {
        log.debug("signUp!");

        CompletableFuture.runAsync(() -> Ebean.save(member), executor)
                .thenApply(result -> asyncResponse.resume(Response.created(URI.create("/member/" + member.getMid())).build()))
                .exceptionally(e -> asyncResponse.resume(Response.status(INTERNAL_SERVER_ERROR).entity(e).build()));

        asyncResponse.setTimeout(3000, TimeUnit.MILLISECONDS);
        asyncResponse.setTimeoutHandler(ar -> ar.resume(Response.status(SERVICE_UNAVAILABLE).entity("Operation timed out").build()));

        log.debug("end!");
    }

    /* change pw (PUT /member/{id})
    * DB 변경
    * 세션 만료*/

    /* withdraw(DELETE /member/{id})
    * DB 변경
    * 세션 만료*/

    /* signIn(POST /member/login)
    * DB 조회
    * 세션 등록*/

}
