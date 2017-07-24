package mps.study;

import mps.study.core.MemberService;
import mps.study.utils.TaskExecutor;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {
    private static final Logger LOGGER = Logger.getLogger(Server.class.getName());
    private static final Level DEBUG = Level.INFO;
    private static final URI ADDRESS = UriBuilder.fromPath("http://localhost:8080").build();

    public static void main(String[] args) throws Exception {
        final HttpServer server = GrizzlyHttpServerFactory.createHttpServer(ADDRESS, create());
        server.start();
    }

    public static ResourceConfig create() {
        ResourceConfig resourceConfig = new ResourceConfig();
        resourceConfig.packages("mps.study");
        resourceConfig.register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(MemberService.class).to(MemberService.class);
                bind(TaskExecutor.class).to(TaskExecutor.class);
            }
        });
        return resourceConfig;
    }
}
