package com.example.laptoptcc.android7_pomodoro.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.laptoptcc.android7_pomodoro.R;
import com.example.laptoptcc.android7_pomodoro.settings.LoginCredentials;
import com.example.laptoptcc.android7_pomodoro.settings.SharedPrefs;
import com.google.gson.Gson;

import static android.content.ContentValues.TAG;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = LoginActivity.class.toString();
    private EditText etUsername;
    private EditText etPassword;
    private Button btRegister;
    private Button btLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        skipLoginAsPossible();

        setContentView(R.layout.activity_login);

        etUsername = (EditText) this.findViewById(R.id.et_username);
        etPassword = (EditText) this.findViewById(R.id.et_password);
        btLogin = (Button) this.findViewById(R.id.bt_login);
        btRegister = (Button) this.findViewById(R.id.bt_register);

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });

        //service-oriented

        //Database <- controller -> sharedPref
        SharedPreferences sharedPreferences =
                this.getSharedPreferences("setting" , MODE_PRIVATE);

        //Save something:
        //Key-value
        //String Username = "admin";
        sharedPreferences.edit().putString("username","admin").commit();

        String username = sharedPreferences.getString("username",null);

        //Object => String
        //Gson gson = new Gson();
        //String json = gson.toJson(loginCredentials);

        //Log.d(TAG,String.format(">>json:%s",json));

        /*LoginCredentials loginCredentials = new LoginCredentials("duy","daisuc");
        //String => Login
        LoginCredentials loginCredentials2 = gson.fromJson(json,LoginCredentials.class);
        Log.d(TAG,String.format("onCreate: object: %s",loginCredentials2));

        Log.d(TAG,String.format("Done reading %s", username));

        Log.d(TAG,"Done Saving");*/

        SharedPrefs sharedPrefs = new SharedPrefs(this);
        sharedPrefs.put(new LoginCredentials("hieu","xxx"));
        Log.d(TAG,String.format("%s",sharedPrefs.getLoginCredentials().toString()));


        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptRegister();
            }
        });
    }

    private void skipLoginAsPossible() {
        if(SharedPrefs.getInstance().getLoginCredentials() != null){
            //gotoTaskActivity();
        }
    }


    private void attemptRegister() {
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        if(username.equals("admin")&& password.equals("admin")){
            Toast.makeText(this,"Registered Successfully", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this,"Username or Password invalid",Toast.LENGTH_LONG).show();
        }
    }

    private void attemptLogin() {
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();

        if(username.equals("admin") && password.equals("admin")){
            // Notifications

            SharedPrefs.getInstance().put(new LoginCredentials(username,password));
            Toast.makeText(this, "Logged in", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this,Setting.class);
            startActivity(intent);
        } else {
            Toast.makeText(this,"Username or Password invalid",Toast.LENGTH_LONG).show();
        }
    }

    private void gotoTaskActivity(){
        Intent intent = new Intent(this,TaskActivity.class);
        startActivity(intent);
    }
}
