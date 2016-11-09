package com.android.exercise.base.retrofit;

import com.android.exercise.base.manager.AppManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 请求基类
 * Created by wangzhen on 16/11/9.
 */

public abstract class APIBaskTask<T> implements Callback<T> {

    private APICallback<T> mCallback;

    public APIBaskTask(APICallback<T> callback) {
        this.mCallback = callback;
        if (mCallback == null) {
            throw new NullPointerException("mCallback == null");
        }
        mCallback.onBefore();
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response != null && response.body() != null) {
            if (response.code() == 200) {
                mCallback.onSuccess(response.body());
            } else {
                mCallback.onError(response.errorBody().toString());
                AppManager.get().getActivity();
            }
        } else {
            mCallback.onFailure();
        }
        mCallback.onAfter();
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        mCallback.onError(t.getMessage());
        mCallback.onAfter();
    }

    /**
     * 实际执行请求的方法
     *
     * @param params 需要传递的参数
     */
    public abstract Call exe(String... params);
}
