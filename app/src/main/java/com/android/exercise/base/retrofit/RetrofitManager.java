package com.android.exercise.base.retrofit;

import com.android.exercise.base.manager.APIManager;
import com.android.exercise.base.retrofit.progress.ProgressInterceptor;
import com.android.exercise.base.retrofit.progress.ProgressListener;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Retrofit 管理类
 * Created by wangzhen on 16/11/9.
 */

public class RetrofitManager {
    public static Retrofit get() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(1000 * 5, TimeUnit.MILLISECONDS)
                .build();
        return new Retrofit.Builder()
                .baseUrl(APIManager.getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    public static Retrofit getProgressClient(ProgressListener listener) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(1000 * 5, TimeUnit.MILLISECONDS)
                .addInterceptor(new ProgressInterceptor(listener))
                .build();
        return new Retrofit.Builder()
                .baseUrl(APIManager.getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }
}
