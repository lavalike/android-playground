package com.android.exercise.ui.activity.biometric.manager.callback;

/**
 * AbsFingerprintCallback
 * Created by wangzhen on 2020/6/4.
 */
public abstract class AbsFingerprintCallback implements OnFingerprintCallback {

    @Override
    public void onError(String error) {

    }

    @Override
    public void notSupport() {

    }

    @Override
    public void noFingerprint() {

    }
}
