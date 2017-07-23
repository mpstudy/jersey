package com.nexon;

import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.core.UriBuilder;

import com.nexon.services.MemberService;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;


public class Server {
    private static final Logger LOGGER = Logger.getLogger(MemberService.class.getName());
    private static final Level DEBUG = Level.INFO;
    private final URI ADDRESS = UriBuilder.fromPath("http://localhost:8080").build();

    public Server() {
        ResourceConfig resourceConfig = new ResourceConfig();
        resourceConfig.packages("com.nexon");
        GrizzlyHttpServerFactory.createHttpServer(ADDRESS, resourceConfig);
        LOGGER.log(DEBUG, "started");
    }

    public static void main(String[] args) {
        Server server = new Server();
    }

}
