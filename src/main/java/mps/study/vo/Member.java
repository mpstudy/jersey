package mps.study.vo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@JsonIgnoreProperties(ignoreUnknown=true)
@Data
@ToString
public class Member {
    private String id;
    private String pw;
    private int code;

    @JsonCreator
    public Member(@JsonProperty("id") String id,
                        @JsonProperty("pw") String pw,
                        @JsonProperty("code") int code) {
        this.id = id;
        this.pw = pw;
        this.code = code;
    }

    public Member(String id, String pw) {
        this.id = id;
        this.pw = pw;
    }
}
