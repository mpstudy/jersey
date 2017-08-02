package mps.study.resources;

import mps.study.utils.TaskExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

@Path("/")
public class CommonResource {
    private static Logger log = LoggerFactory.getLogger(CommonResource.class);

    @Inject
    private TaskExecutor executor;

    @GET
    public Response index() {
        log.debug("index!");

        return Response.seeOther(
                UriBuilder.fromPath("/static/index.html").build()
        ).build();
    }
}
