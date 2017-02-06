package com.example.laptoptcc.session4.networks.jsonmodels;

import com.google.gson.annotations.SerializedName;

/**
 * Created by laptopTCC on 2/6/2017.
 */

public class LoginResponseJson {
    @SerializedName("access_token")
    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }
}
