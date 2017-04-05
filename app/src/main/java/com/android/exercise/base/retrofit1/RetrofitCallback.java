package com.android.exercise.base.retrofit1;

/**
 * Created by wangzhen on 2017/4/5.
 */

public interface RetrofitCallback {
    /**
     * 请求执行之前
     */
    void onBefore();

    /**
     * 请求有误
     *
     * @param error
     */
    void onError(String error);

    /**
     * 请求失败
     */
    void onFailure();

    /**
     * 请求完毕后
     */
    void onAfter();
}
