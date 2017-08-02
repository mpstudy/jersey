package mps.study.model;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.time.ZonedDateTime;

@Entity
@XmlRootElement
public class Member implements Serializable {
    @Id
    private String mid;
    private String firstName;
    private String lastName;
    private ZonedDateTime joinDate;
    private boolean isValid;
    private boolean isWithdraw;
    private ZonedDateTime withDrawDate;

    @Embedded
    private Privacy privacy;

    public Member() {}

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public ZonedDateTime getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(ZonedDateTime joinDate) {
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

    public ZonedDateTime getWithDrawDate() {
        return withDrawDate;
    }

    public void setWithDrawDate(ZonedDateTime withDrawDate) {
        this.withDrawDate = withDrawDate;
    }

    public Privacy getPrivacy() {
        return privacy;
    }

    public void setPrivacy(Privacy privacy) {
        this.privacy = privacy;
    }
}
