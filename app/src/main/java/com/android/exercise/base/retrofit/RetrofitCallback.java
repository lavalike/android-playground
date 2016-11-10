package com.android.exercise.base.retrofit;

import android.app.Activity;

import com.android.exercise.R;
import com.android.exercise.base.manager.AppManager;
import com.android.exercise.base.retrofit.callback.CommonCallback;
import com.android.exercise.util.NetworkUtil;

import java.net.SocketTimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 请求基类
 * Created by wangzhen on 16/11/9.
 */

public abstract class RetrofitCallback<T> implements Callback<T> {

    private CommonCallback<T> mCallback;

    public RetrofitCallback(CommonCallback<T> callback) {
        if (callback == null) {
            throw new NullPointerException("mCallback == null");
        }
        this.mCallback = callback;
        mCallback.onBefore();
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response.isSuccessful()) {
            mCallback.onSuccess(response.body());
        } else {
            mCallback.onFailure();
        }
        mCallback.onAfter();
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        Activity activity = AppManager.get().getActivity();
        if (!NetworkUtil.isNetworkAvailable(activity)) {
            mCallback.onError(activity.getString(R.string.error_network_failure));
        } else if (t instanceof SocketTimeoutException) {
            mCallback.onError(activity.getString(R.string.error_network_timeout));
        } else {
            mCallback.onError(t.getMessage());
        }
        mCallback.onAfter();
    }

    /**
     * 实际执行请求的方法
     *
     * @param params 需要传递的参数
     */
    public abstract Call exe(String... params);
}
