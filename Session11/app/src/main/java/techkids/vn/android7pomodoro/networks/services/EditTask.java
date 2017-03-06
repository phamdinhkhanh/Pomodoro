package techkids.vn.android7pomodoro.networks.services;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import techkids.vn.android7pomodoro.networks.TaskResponseJson;

/**
 * Created by laptopTCC on 2/25/2017.
 */

public interface EditTask {
    @PUT("task/{local_id}")
    Call<TaskResponseJson> editTask(@Path("local_id") String localID, @Body TaskResponseJson taskEdited);
}
