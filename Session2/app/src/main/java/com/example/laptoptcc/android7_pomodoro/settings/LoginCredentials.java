package com.example.laptoptcc.android7_pomodoro.settings;

/**
 * Created by laptopTCC on 1/14/2017.
 */

public class LoginCredentials{
    private String username;
    private String password;

    public LoginCredentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}