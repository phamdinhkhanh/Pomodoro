package com.example.laptoptcc.session4.networks.services;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by laptopTCC on 2/6/2017.
 */

public class NetContext {
    private Retrofit retrofit;
    public NetContext(){
        retrofit = new Retrofit.Builder()
                .baseUrl("http://a-task.herokuapp.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
