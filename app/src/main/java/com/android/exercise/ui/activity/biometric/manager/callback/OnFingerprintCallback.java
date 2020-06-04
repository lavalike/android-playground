package com.android.exercise.ui.activity.biometric.manager.callback;

/**
 * OnFingerprintCallback
 * Created by wangzhen on 2020/6/4.
 */
public interface OnFingerprintCallback {
    void onSuccess();

    void onError(String error);

    void notSupport();

    void noFingerprint();
}
