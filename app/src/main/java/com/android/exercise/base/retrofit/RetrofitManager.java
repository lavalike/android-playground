package com.android.exercise.base.retrofit;

import com.android.exercise.base.manager.APIManager;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Retrofit 管理类
 * Created by wangzhen on 16/11/9.
 */

public class RetrofitManager {
    private static RetrofitManager mInstance;
    private static OkHttpClient mClient;
    private final Retrofit mRetrofit;

    static {
        //初始化OkHttp相关,如配置拦截器
        mClient = new OkHttpClient.Builder()
//                .addInterceptor()
                .connectTimeout(1000 * 5, TimeUnit.MILLISECONDS)
                .build();
    }

    /**
     * baseUrl默认使用APIManager.getBaseUrl()
     */
    public RetrofitManager() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(APIManager.getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .client(mClient)
                .build();
    }

    /**
     * 使用传入的baseUrl
     *
     * @param baseUrl
     */
    public RetrofitManager(String baseUrl) {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(mClient)
                .build();
    }

    public static RetrofitManager getInstance() {
        if (mInstance == null) {
            synchronized (RetrofitManager.class) {
                if (mInstance == null) {
                    mInstance = new RetrofitManager();
                }
            }
        }
        return mInstance;
    }

    public static RetrofitManager getInstance(String baseUrl) {
        if (mInstance == null) {
            synchronized (RetrofitManager.class) {
                if (mInstance == null) {
                    mInstance = new RetrofitManager(baseUrl);
                }
            }
        }
        return mInstance;
    }

    public <T> T create(Class<T> service) {
        return mRetrofit.create(service);
    }
}
