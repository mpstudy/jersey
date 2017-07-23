package com.nexon;

import javax.ws.rs.core.Application;

import com.nexon.resources.MemberResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;


public class Mission2ResourceTest extends JerseyTest {

    @Override
    protected Application configure() {
        return new ResourceConfig(MemberResource.class);
    }

/*    @Test
    public void shouldReturnMemberStatus() {
        Member member = new Member("ant103@nexon.co.kr","moda83908", 0);
        final Response res = target("member").request().post(Entity.json(member));
        assertEquals(Response.Status.OK.toString(), String.valueOf(res.getStatus()));
    }*/
}