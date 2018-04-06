package com.example.android.messages.Api;

import android.content.Context;

import com.example.android.messages.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by pc on 4/6/2018.
 */

public class RestApi {
    public static final int request_max_time_in_seconds = 20;
    private Context activity;

    public RestApi(Context activity) {
        this.activity = activity;
    }

    public Retrofit getRetrofitInstance() {
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(request_max_time_in_seconds, TimeUnit.SECONDS)
                .connectTimeout(request_max_time_in_seconds, TimeUnit.SECONDS)
                .build();
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    public ApiService request() {
        return getRetrofitInstance().create(ApiService.class);
    }

    /* public Call<MsgModel> getMsg(){
    return request().getNowPlaying();*/
}