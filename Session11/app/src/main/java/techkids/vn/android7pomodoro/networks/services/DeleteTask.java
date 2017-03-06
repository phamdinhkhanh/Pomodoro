package techkids.vn.android7pomodoro.networks.services;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Path;
import techkids.vn.android7pomodoro.networks.jsonmodels.DeleteResponseJson;

/**
 * Created by laptopTCC on 2/25/2017.
 */

public interface DeleteTask {
    @DELETE("task/{local_id}")
    Call<DeleteResponseJson> deleteTask(@Path("local_id") String localID);
}
