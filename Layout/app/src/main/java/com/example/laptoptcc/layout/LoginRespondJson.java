package com.example.laptoptcc.layout;

import com.google.gson.annotations.SerializedName;

/**
 * Created by laptopTCC on 2/7/2017.
 */

public class LoginRespondJson {
    @SerializedName("access_token")
    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }
}
