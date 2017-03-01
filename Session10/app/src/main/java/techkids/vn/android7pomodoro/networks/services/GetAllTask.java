package techkids.vn.android7pomodoro.networks.services;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import techkids.vn.android7pomodoro.networks.TaskResponseJson;

/**
 * Created by laptopTCC on 2/21/2017.
 */

public interface GetAllTask {
    @GET("task")
    Call<List<TaskResponseJson>> getAllTask();
}
