package com.nexon.services;


import com.nexon.vo.Member;

import javax.ws.rs.core.Response;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MemberService {
    private static final Logger LOGGER = Logger.getLogger(MemberService.class.getName());
    private static final Level DEBUG = Level.INFO;

    public static Response.Status register(Member member) {
        LOGGER.log(DEBUG, member.toString());
        return Response.Status.OK;
    }
}
