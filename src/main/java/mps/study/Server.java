package mps.study;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.EbeanServer;
import mps.study.utils.TaskExecutor;
import org.avaje.agentloader.AgentLoader;
import org.glassfish.grizzly.http.server.CLStaticHttpHandler;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.UriBuilder;
import java.awt.*;
import java.io.IOException;
import java.net.URI;

public class Server {
    private static Logger log = LoggerFactory.getLogger(Server.class);
    static final URI BASE_URI = UriBuilder.fromUri("http://localhost/").port(8080).build();

    public static void main(String[] args) throws Exception {
        setupEbean();
        final HttpServer server =
                GrizzlyHttpServerFactory.createHttpServer(
                        BASE_URI,
                        ResourceConfig.forApplicationClass(RsResourceConfig.class));

        // static assets
        server.getServerConfiguration().addHttpHandler(
                new CLStaticHttpHandler(Server.class.getClassLoader(), "/assets/"), "/static");

        Runtime.getRuntime().addShutdownHook(new Thread(server::shutdownNow));
        openBrowser();

        log.info("Start grizzly. Press any key to exit.");
        System.in.read();
        System.exit(0);
    }

    private static void openBrowser() {
        try {
            if (Desktop.isDesktopSupported())
                Desktop.getDesktop().browse(Server.BASE_URI);
        } catch (IOException ignore) {
            log.error("fail to launch browser.", ignore);
        }
    }


    public static class RsResourceConfig extends ResourceConfig {

        public RsResourceConfig() {
            packages("mps.study.resources");

            register(new AbstractBinder() {
                @Override
                protected void configure() {
                    bind(new TaskExecutor()).to(TaskExecutor.class);
                }
            });
        }
    }

    private static EbeanServer setupEbean() {

        boolean success = AgentLoader.loadAgentFromClasspath(
                "avaje-ebeanorm-agent", "debug=1;packages=mps.study.model.**");

        if (!success) {
            log.error("ebeanorm agent not found - not dynamically loaded");
        }
        return Ebean.getServer("h2");

    }
}
