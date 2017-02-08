package com.example.laptoptcc.layout;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by laptopTCC on 2/7/2017.
 */

public interface LoginServices {
    @POST("login")
    Call<LoginRespondJson> login(@Body RequestBody body);
}

