package mps.study.core;

import org.jvnet.hk2.annotations.Service;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class MemberService {
    private static final Logger LOGGER = Logger.getLogger(MemberService.class.getName());
    private static final Level DEBUG = Level.INFO;

    public boolean add(String json) {
        LOGGER.log(DEBUG, "add!");
        return true;
    }

    public String find(String id) {
        return id;
    }

    public String remove(String json) {
        return json;
    }

    public String update(String id) {
        return id;
    }
}
