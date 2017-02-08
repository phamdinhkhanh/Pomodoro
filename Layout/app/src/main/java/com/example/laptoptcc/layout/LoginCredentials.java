package com.example.laptoptcc.layout;

/**
 * Created by laptopTCC on 2/7/2017.
 */

public class LoginCredentials {
    public String accesstoken;
    public String username;
    public String password;

    public String getAccesstoken() {
        return accesstoken;
    }

    public void setAccesstoken(String accesstoken) {
        this.accesstoken = accesstoken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LoginCredentials(String accesstoken, String username, String password) {
        this.accesstoken = accesstoken;
        this.username = username;
        this.password = password;
    }

    public String toString(){
        return "{"+"username="+this.username+'\''+
                "password="+this.password+'\''+
                "accesstoken="+this.accesstoken;
    }
}
