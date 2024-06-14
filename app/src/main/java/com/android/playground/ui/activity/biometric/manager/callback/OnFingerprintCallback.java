package com.android.playground.ui.activity.biometric.manager.callback;

/**
 * OnFingerprintCallback
 * Created by wangzhen on 2020/6/4.
 */
public interface OnFingerprintCallback {
    void onSuccess();

    void onFail();

    void notSupport();

    void noFingerprint();
}
