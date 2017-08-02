package mps.study.vo;

public class JsonResponse {
    private int code;
    private String detail;
    private Object data;

    public JsonResponse(int code, String detail, Object data) {
        this.code = code;
        this.detail = detail;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
