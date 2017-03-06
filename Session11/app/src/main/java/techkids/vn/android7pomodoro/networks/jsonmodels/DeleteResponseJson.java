package techkids.vn.android7pomodoro.networks.jsonmodels;

import com.google.gson.annotations.SerializedName;

/**
 * Created by laptopTCC on 2/25/2017.
 */

public class DeleteResponseJson {
    @SerializedName("message")
    public String message;
    @SerializedName("code")
    public int code;

    public DeleteResponseJson(String message, int code) {
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
