package mps.study.model;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class Privacy implements Serializable {
    private String pw;

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }
}
