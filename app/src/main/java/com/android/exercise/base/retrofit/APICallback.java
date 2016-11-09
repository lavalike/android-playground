package com.android.exercise.base.retrofit;

/**
 * 普通回调接口的实现 GET POST请求
 * Created by wangzhen on 16/11/9.
 */

public abstract class APICallback<T> implements APIInterface {
    @Override
    public void onFailure() {

    }

    /**
     * 请求成功
     *
     * @param data
     */
    protected abstract void onSuccess(T data);

    @Override
    public void onError(String error) {

    }

    @Override
    public void onAfter() {

    }

    @Override
    public void onBefore() {

    }
}
