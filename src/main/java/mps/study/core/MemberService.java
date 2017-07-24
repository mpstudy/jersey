package mps.study.core;


import com.fasterxml.jackson.databind.ObjectMapper;
import mps.study.utils.DataServiceHelper;
import mps.study.vo.Member;
import org.jvnet.hk2.annotations.Service;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class MemberService {
    private static final Logger LOGGER = Logger.getLogger(MemberService.class.getName());
    private static final Level DEBUG = Level.INFO;

    public boolean add(String json) {
        Member member = null;
        boolean result = false;
        try {
            ObjectMapper mapper = new ObjectMapper();
            member = mapper.readValue(json, Member.class);
            LOGGER.log(DEBUG, "{0}", member.toString());
            String sql = String.format("insert into member(id,pw) values ('{}','{}')", member.getId(), member.getPw());
            result = DataServiceHelper.getInstance().execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public String find(String id) {

        LOGGER.log(DEBUG, "{0}", id);
        return id;
    }

    public String remove(String json) {
        Member member = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            member = mapper.readValue(json, Member.class);
            LOGGER.log(DEBUG, "{0}", json);
            LOGGER.log(DEBUG, "{0}", member.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return member.getId();
    }
}
