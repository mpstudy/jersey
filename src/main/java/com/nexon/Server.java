package com.nexon;

import java.net.URI;

import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;


public class Server {

    private final URI ADDRESS = UriBuilder.fromPath("http://localhost:8080").build();

    public Server() {
        ResourceConfig resourceConfig = new ResourceConfig();
        resourceConfig.packages("com.nexon");
        GrizzlyHttpServerFactory.createHttpServer(ADDRESS, resourceConfig);
    }


    public static void main(String[] args) {
        Server server = new Server();
    }

}
