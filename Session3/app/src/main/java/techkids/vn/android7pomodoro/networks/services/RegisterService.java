package techkids.vn.android7pomodoro.networks.services;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import techkids.vn.android7pomodoro.networks.jsonmodels.RegisterResponseJson;

/**
 * Created by laptopTCC on 1/21/2017.
 */

public interface RegisterService {
    @POST("register")
    Call<RegisterResponseJson> register(@Body RequestBody body);
}
