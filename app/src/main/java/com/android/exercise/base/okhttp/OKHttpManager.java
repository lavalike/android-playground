package com.android.exercise.base.okhttp;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by wangzhen on 2017/2/8.
 */

public class OKHttpManager {
    private static OkHttpClient mClient;

    public static OkHttpClient getClient() {
        if (mClient == null) {
            synchronized (OKHttpManager.class) {
                if (mClient == null) {
                    OkHttpClient.Builder builder = new OkHttpClient.Builder()
                            .connectTimeout(15, TimeUnit.SECONDS)
                            .readTimeout(15, TimeUnit.SECONDS)
                            .writeTimeout(15, TimeUnit.SECONDS);
                    mClient = builder.build();
                }
            }
        }
        return mClient;
    }
}
