package com.nexon;

import static org.junit.Assert.*;

import javax.ws.rs.core.Application;

import com.nexon.resources.HelloResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;


public class HelloResourceTest extends JerseyTest {

    @Override
    protected Application configure() {
        return new ResourceConfig(HelloResource.class);
    }

    @Test
    public void shouldReturnHellotest() {
        final String hello = target("hello").request().get(String.class);
        assertEquals("Hello!", hello);
    }
}