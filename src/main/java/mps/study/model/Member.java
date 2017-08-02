package mps.study.model;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@XmlRootElement
public class Member implements Serializable {
    @Id
    private String mid;
    @Column(name = "pw", nullable = false, updatable = true, insertable = true)
    private String pw;
    @Column(name = "joinDate", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp joinDate;
    private boolean isValid = false;
    private boolean isWithdraw = false;
    @Column(name = "withDrawDate", nullable = true, updatable = true, insertable = true, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp withDrawDate;

    @Embedded
    private Privacy privacy;

    public Member() {}


    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public Timestamp getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Timestamp joinDate) {
        this.joinDate = joinDate;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public boolean isWithdraw() {
        return isWithdraw;
    }

    public void setWithdraw(boolean withdraw) {
        isWithdraw = withdraw;
    }

    public Timestamp getWithDrawDate() {
        return withDrawDate;
    }

    public void setWithDrawDate(Timestamp withDrawDate) {
        this.withDrawDate = withDrawDate;
    }

    public Privacy getPrivacy() {
        return privacy;
    }

    public void setPrivacy(Privacy privacy) {
        this.privacy = privacy;
    }
}
