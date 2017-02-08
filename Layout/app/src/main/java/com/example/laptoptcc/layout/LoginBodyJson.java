package com.example.laptoptcc.layout;

import com.google.gson.annotations.SerializedName;

/**
 * Created by laptopTCC on 2/7/2017.
 */

public class LoginBodyJson {
    @SerializedName("username")
    private String username;
    @SerializedName("password")
    private String password;

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

    public LoginBodyJson(String username, String password){
        this.username = username;
        this.password = password;
    }

    public String toString(){
        return super.toString();
    }
}
