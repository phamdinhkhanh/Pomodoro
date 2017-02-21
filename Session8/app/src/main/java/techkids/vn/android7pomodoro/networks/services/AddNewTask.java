package techkids.vn.android7pomodoro.networks.services;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import techkids.vn.android7pomodoro.networks.TaskResponseJson;

/**
 * Created by laptopTCC on 2/21/2017.
 */

public interface AddNewTask {
    @POST ("task")
    Call<TaskResponseJson> addNewTask(@Body RequestBody requestBody);
}
