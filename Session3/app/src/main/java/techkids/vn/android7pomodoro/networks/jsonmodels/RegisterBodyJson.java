package techkids.vn.android7pomodoro.networks.jsonmodels;

/**
 * Created by laptopTCC on 1/21/2017.
 */

public class RegisterBodyJson {
    private String username;
    private String password;

    public RegisterBodyJson(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
