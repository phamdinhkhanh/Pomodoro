package techkids.vn.android7pomodoro.networks.jsonmodels;

/**
 * Created by laptopTCC on 1/21/2017.
 */

public class RegisterResponseJson {
    private int code;
    private String measage;
    private String token;

    public void setCode(int code) {
        this.code = code;
    }

    public void setMeasage(String measage) {
        this.measage = measage;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getCode() {
        return code;
    }

    public String getMeasage() {
        return measage;
    }

    public String getToken() {
        return token;
    }

    public RegisterResponseJson(int code, String measage, String token) {
        this.code = code;
        this.measage = measage;
        this.token = token;
    }
}
