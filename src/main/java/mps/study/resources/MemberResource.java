package mps.study.resources;

import com.avaje.ebean.Ebean;
import mps.study.model.Member;
import mps.study.utils.TaskExecutor;
import mps.study.vo.JsonResponse;
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
import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static javax.ws.rs.core.Response.Status.*;

@Path("member")
public class MemberResource {
    private static Logger log = LoggerFactory.getLogger(MemberResource.class);

    @Inject
    private TaskExecutor executor;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    // @ManagedAsync 의 경우 해당 메소드만을 별도의 스레드로 빠뜨려 작업을 진행 한다.
    // 이럴 경우 해당 메소드에서 하나 이상의 비동기 작업을 걸기가 어렵다.
    // CompletableFuture를 사용하면 하나 이상의 비동기 작업을 걸어 각각의 작업 결과물을 동시에 처리 할 수 있다.
    // https://access.redhat.com/documentation/en-US/Red_Hat_JBoss_Fuse/6.2/html/Apache_CXF_Development_Guide/JAXRSAsyncResponse.html#JAXRSAsyncResponse-Processing-FigTMAP
    public void findMember(@DefaultValue("") @HeaderParam("X-Sid") String sid,
                           @Suspended final AsyncResponse asyncResponse) {
        log.debug("findMember!");

        CompletableFuture.supplyAsync(() -> {
            Member current = Ebean.find(Member.class)
                            .where()
                            .eq("sid", sid)
                            .eq("isWithdraw", false)
                            .findUnique();
            if (Objects.isNull(current)) {
                return makeResponse(NOT_FOUND, "Not found member.", null);
            }
            if (!Objects.equals(current.getSid(), sid)
                    || current.getExpireDate().before(getNow())) {
                return makeResponse(UNAUTHORIZED, "unauthorized.", null);
            }
            return makeResponse(ACCEPTED, "accepted.", current);
        }, executor)
                .thenApply(asyncResponse::resume)
                .exceptionally(e -> asyncResponse.resume(Response.status(INTERNAL_SERVER_ERROR).entity(e).build()));

        asyncResponse.setTimeout(3000, TimeUnit.MILLISECONDS);
        asyncResponse.setTimeoutHandler(ar -> ar.resume(Response.status(SERVICE_UNAVAILABLE).entity("Operation timed out").build()));

        log.debug("end!");
    }

    @POST
    @Path("signUp")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void signUp(Member member,
                       @Suspended final AsyncResponse asyncResponse) throws IOException {
        log.debug("signUp!");

        CompletableFuture.supplyAsync(() -> {
            Member current = Ebean.find(Member.class)
                    .where()
                    .eq("uid", member.getUid())
                    .eq("isWithdraw", false)
                    .findUnique();
            if (Objects.nonNull(current)) {
                return makeResponse(NOT_ACCEPTABLE, "Not Acceptable.", null);
            }
            Ebean.save(member);
            return makeResponse(CREATED, "created.", member);
        }, executor)
                .thenApply(asyncResponse::resume)
                .exceptionally(e -> asyncResponse.resume(Response.status(INTERNAL_SERVER_ERROR).entity(e).build()));

        asyncResponse.setTimeout(3000, TimeUnit.MILLISECONDS);
        asyncResponse.setTimeoutHandler(ar -> ar.resume(Response.status(SERVICE_UNAVAILABLE).entity("Operation timed out").build()));

        log.debug("end!");
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void editMember(@DefaultValue("") @HeaderParam("X-Sid") String sid,
                           Member member,
                           @Suspended final AsyncResponse asyncResponse) throws IOException {
        log.debug("editMember!");

        CompletableFuture.supplyAsync(() -> {
            Member current = Ebean.find(Member.class)
                    .where()
                    .eq("uid", member.getUid())
                    .eq("isWithdraw", false)
                    .findUnique();
            if (Objects.isNull(current)) {
                return makeResponse(NOT_FOUND, "Not found member.", null);
            }
            if (!Objects.equals(current.getSid(), sid)
                    || current.getExpireDate().before(getNow())) {
                return makeResponse(UNAUTHORIZED, "unauthorized.", null);
            }
            current.setPw(member.getPw());
            Ebean.update(current);
            return makeResponse(ACCEPTED, "accepted.", current);
        }, executor)
                .thenApply(asyncResponse::resume)
                .exceptionally(e -> asyncResponse.resume(Response.status(INTERNAL_SERVER_ERROR).entity(e).build()));

        asyncResponse.setTimeout(3000, TimeUnit.MILLISECONDS);
        asyncResponse.setTimeoutHandler(ar -> ar.resume(Response.status(SERVICE_UNAVAILABLE).entity("Operation timed out").build()));

        log.debug("end!");
    }

    @PUT
    @Path("signOut")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void signOut(@DefaultValue("") @HeaderParam("X-Sid") String sid,
                        @Suspended final AsyncResponse asyncResponse) throws IOException {
        log.debug("signOut!");

        CompletableFuture.supplyAsync(() -> {
            Member current = Ebean.find(Member.class)
                    .where()
                    .eq("sid", sid)
                    .eq("isWithdraw", false)
                    .findUnique();
            if (Objects.isNull(current)) {
                return makeResponse(NOT_FOUND, "Not found member.", null);
            }
            if (!Objects.equals(current.getSid(), sid)
                    || current.getExpireDate().before(getNow())) {
                return makeResponse(UNAUTHORIZED, "unauthorized.", null);
            }
            current.setExpireDate(getNow());
            Ebean.update(current);
            return makeResponse(ACCEPTED, "accepted.", current);
        }, executor)
                .thenApply(asyncResponse::resume)
                .exceptionally(e -> asyncResponse.resume(Response.status(INTERNAL_SERVER_ERROR).entity(e).build()));

        asyncResponse.setTimeout(3000, TimeUnit.MILLISECONDS);
        asyncResponse.setTimeoutHandler(ar -> ar.resume(Response.status(SERVICE_UNAVAILABLE).entity("Operation timed out").build()));

        log.debug("end!");
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public void removeMember(@DefaultValue("") @HeaderParam("X-Sid") String sid,
                             @Suspended final AsyncResponse asyncResponse) throws IOException {
        log.debug("removeMember!");

        CompletableFuture.supplyAsync(() -> {
            Member current = Ebean.find(Member.class)
                    .where()
                    .eq("sid", sid)
                    .eq("isWithdraw", false)
                    .findUnique();
            if (Objects.isNull(current)) {
                return makeResponse(NOT_FOUND, "Not found member.", null);
            }
            if (!Objects.equals(current.getSid(), sid)
                    || current.getExpireDate().before(getNow())) {
                return makeResponse(UNAUTHORIZED, "unauthorized.", null);
            }
            current.setWithdraw(true);
            current.setWithDrawDate(getNow());
            current.setSid("");
            current.setExpireDate(getNow());
            Ebean.update(current);
            return makeResponse(ACCEPTED, "accepted.", current);
        }, executor)
                .thenApply(asyncResponse::resume)
                .exceptionally(e -> asyncResponse.resume(Response.status(INTERNAL_SERVER_ERROR).entity(e).build()));

        asyncResponse.setTimeout(3000, TimeUnit.MILLISECONDS);
        asyncResponse.setTimeoutHandler(ar -> ar.resume(Response.status(SERVICE_UNAVAILABLE).entity("Operation timed out").build()));

        log.debug("end!");
    }

    @POST
    @Path("signIn")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void signIn(Member member,
                       @Suspended final AsyncResponse asyncResponse) throws IOException {
        log.debug("signIn!");

        CompletableFuture.supplyAsync(() -> {
            Member current = Ebean.find(Member.class)
                            .where()
                            .eq("uid", member.getUid())
                            .eq("pw", member.getPw())
                            .eq("isWithdraw", false)
                            .findUnique();

            if (Objects.isNull(current)) {
                return makeResponse(NOT_FOUND, "Not found member.", null);
            }

            current.setSid(getSid());
            current.setExpireDate(getExpireDate());
            Ebean.update(current);
            return makeResponse(ACCEPTED, "accepted.", current);
        }, executor)
                .thenApply(asyncResponse::resume)
                .exceptionally(e -> asyncResponse.resume(Response.status(INTERNAL_SERVER_ERROR).entity(e).build()));

        asyncResponse.setTimeout(3000, TimeUnit.MILLISECONDS);
        asyncResponse.setTimeoutHandler(ar -> ar.resume(Response.status(SERVICE_UNAVAILABLE).entity("Operation timed out").build()));

        log.debug("end!");
    }

    private Timestamp getExpireDate() {
        return Timestamp.from(LocalDateTime.now().atZone(ZoneOffset.ofHours(1)).toInstant());
    }

    private String getSid() {
        return new BigInteger(130, new SecureRandom()).toString(32);
    }

    private Response makeResponse(Response.Status status, String message, Object detail) {
        return Response.status(status).entity(new JsonResponse(status.getStatusCode(), message, detail)).build();
    }

    private Timestamp getNow() {
        return new Timestamp(System.currentTimeMillis());
    }
}

