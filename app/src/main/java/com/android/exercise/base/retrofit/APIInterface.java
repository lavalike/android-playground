package com.android.exercise.base.retrofit;

/**
 * 自定义回调接口
 * Created by wangzhen on 16/11/9.
 */

public interface APIInterface {

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
