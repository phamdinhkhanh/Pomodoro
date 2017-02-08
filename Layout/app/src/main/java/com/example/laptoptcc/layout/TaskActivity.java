package com.example.laptoptcc.layout;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by laptopTCC on 2/7/2017.
 */

public class TaskActivity extends AppCompatActivity {
    private static final String TAG = TaskActivity.class.toString();
    private EditText etPassword;
    private EditText etUsername;
    private Button btLogin;
    private Button btRegister;
    private String username;
    private String password;
    private String accessToken;
    private Retrofit retrofit;

    public void onCreate(Bundle savedInStanceState){
        super.onCreate(savedInStanceState);
        setContentView(R.layout.activity_main);
        btLogin = (Button) this.findViewById(R.id.bt_login);
        btRegister = (Button) this.findViewById(R.id.bt_register);
        etUsername = (EditText) this.findViewById(R.id.et_username);
        etPassword = (EditText) this.findViewById(R.id.et_password);
        btLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                attempLogin();
            }
        });
    }

    private void sendLogin(String username, String password) {
        //1. create (base url)
        retrofit = new Retrofit.Builder()
                   .baseUrl("http://a-task.herokuapp.com/api/")
                   .addConverterFactory(GsonConverterFactory.create())
                   .build();
        //2. create services
        LoginServices loginServices = retrofit.create(LoginServices.class);
        MediaType jsonType = MediaType.parse("application/json");
        String loginJson = new Gson().toJson(new LoginBodyJson(this.username, this.password));
        RequestBody loginBody = RequestBody.create(jsonType,loginJson);

        //enqueue tự động mở luồng phụ.
        //execute chạy trên luồng chính.
        //3. create call.
        Call<LoginRespondJson> loginCall = loginServices.login(loginBody);
        loginCall.enqueue(new Callback<LoginRespondJson>() {
            @Override
            public void onResponse(Call<LoginRespondJson> call, Response<LoginRespondJson> response) {
                LoginRespondJson loginResponseJson = response.body();
                if (loginResponseJson == null) {
                    Log.d(TAG, "onResponse: could not parse body");
                } else {
                    Log.d(TAG, "onResponse: oh year");
                    if (response.code() == 200) {
                        accessToken = loginResponseJson.getAccessToken();
                        onLoginSuccess();
                    }
                }

                Log.d(TAG, "onResponse");
            }

            @Override
            public void onFailure(Call<LoginRespondJson> call, Throwable t) {
                Log.d(TAG, "onFailure");
            }
        });
    }

    private void onLoginSuccess() {
        //SharedPref.getInstance().put(new LoginCredentials(username, password,accessToken));
        Toast.makeText(this, "Logged in", Toast.LENGTH_SHORT).show();
        gotoTaskActivity();
    }

    private void attempLogin() {
        username = etUsername.getText().toString();
        password = etPassword.getText().toString();
        sendLogin(username,password);
        Log.d(TAG,String.format("%s %s",username,password));
    }

    private void gotoTaskActivity() {
        Intent intent = new Intent(this, TaskActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);//báo cho k lưu vào stack.
        startActivity(intent);
    }
}
