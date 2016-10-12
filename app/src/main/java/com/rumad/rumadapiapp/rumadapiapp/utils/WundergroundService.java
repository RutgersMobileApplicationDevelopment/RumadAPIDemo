package com.rumad.rumadapiapp.rumadapiapp.utils;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by shreyashirday on 10/11/16.
 */
public interface WundergroundService {

    @GET("forecast/q/{state}/{city}.json")
    Call<ResponseBody> getForecast(@Path("state") String state, @Path("city") String city);

}
