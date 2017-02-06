package com.example.laptoptcc.session4.networks.services;

import com.example.laptoptcc.session4.networks.jsonmodels.LoginResponseJson;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by laptopTCC on 2/6/2017.
 */

public interface LoginServices {
    @POST("login")
    Call<LoginResponseJson> login(@Body RequestBody body);
}
