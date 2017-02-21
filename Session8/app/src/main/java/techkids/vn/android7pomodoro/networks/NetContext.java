package techkids.vn.android7pomodoro.networks;

import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import techkids.vn.android7pomodoro.activities.LoginActivity;
import techkids.vn.android7pomodoro.databases.DbContext;
import techkids.vn.android7pomodoro.databases.models.Task;
import techkids.vn.android7pomodoro.networks.jsonmodels.LoginBodyJson;
import techkids.vn.android7pomodoro.networks.services.AddNewTask;
import techkids.vn.android7pomodoro.networks.services.GetAllTask;
import techkids.vn.android7pomodoro.settings.SharedPrefs;

/**
 * Created by apple on 1/18/17.
 */

public class NetContext {
    private static String TAG = NetContext.class.toString();
    private Retrofit retrofit;

    public NetContext() {
        OkHttpClient.Builder httpClient = new OkHttpClient().newBuilder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                Request request = original.newBuilder()
                        .header("Authorization", "JWT "+ SharedPrefs.getInstance().getAccessToken())
                        .build();
                return chain.proceed(request);
            }
        });

        OkHttpClient client = httpClient.build();
        retrofit = new Retrofit.Builder()
                .baseUrl("http://a-task.herokuapp.com/api/")
                .addConverterFactory(GsonConverterFactory.create()).client(client)
                .build();
    }

    public void getAllTask(){
        //1.create services:
        GetAllTask getAllTaskServices = retrofit.create(GetAllTask.class);
        final ArrayList<TaskResponseJson> list = new ArrayList<>();
        //2.Create respond body:
        getAllTaskServices.getAllTask().enqueue(new Callback<List<TaskResponseJson>>() {
            @Override
            public void onResponse(Call<List<TaskResponseJson>> call, Response<List<TaskResponseJson>> response) {
                if(response.body() != null){
                    TaskResponseJson taskResponseJson;
                    for(TaskResponseJson task: response.body()){
                            taskResponseJson = new TaskResponseJson(
                                    task.getLocal_id(),
                                    task.getName(),
                                    task.getPaymentPerHour(),
                                    task.getDue_date(),
                                    task.isDone(),
                                    task.getId(),
                                    task.getColor()
                                    );
                        Log.d(TAG,String.format("onResponseBody to getAllTask: %s",task.toString()));
                        list.add(taskResponseJson);
                        Task task1 = new Task(task.getName(),task.getColor(),task.getPaymentPerHour(),task.isDone());
                    }
                } else {
                    Log.d(TAG,"couldn't parse the body");
                }
            }

            @Override
            public void onFailure(Call<List<TaskResponseJson>> call, Throwable t) {
                Log.d(TAG,"onFailure");
            }
        });
    }

    public void addNewTask(){
        //1.Create Services:
        AddNewTask addNewTask = retrofit.create(AddNewTask.class);
        LoginBodyJson loginBodyJson = LoginActivity.loginBodyJson;
        MediaType mediaType = MediaType.parse("application/json");
        String loginBody = (new Gson()).toJson(loginBodyJson);
        RequestBody requestBody = RequestBody.create(mediaType,loginBody);
        //2.implement services:
        addNewTask.addNewTask(requestBody).enqueue(new Callback<TaskResponseJson>() {
            @Override
            public void onResponse(Call<TaskResponseJson> call, Response<TaskResponseJson> response) {
                if(response.body() != null){
                    TaskResponseJson taskResponseJson = response.body();
                    Log.d(TAG,String.format("onResponse to add: %s",taskResponseJson.toString()));
                    DbContext.instance.addTask(new Task(taskResponseJson.getName()
                            ,"#FFFFFF"
                            ,taskResponseJson.getPaymentPerHour()
                            ,taskResponseJson.isDone()));
                } else {
                    Log.d(TAG,"Failure to add");
                }
            }

            @Override
            public void onFailure(Call<TaskResponseJson> call, Throwable t) {

            }
        });
    }
}
