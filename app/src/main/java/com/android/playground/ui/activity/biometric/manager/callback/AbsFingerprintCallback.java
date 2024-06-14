package com.android.playground.ui.activity.biometric.manager.callback;

/**
 * AbsFingerprintCallback
 * Created by wangzhen on 2020/6/4.
 */
public abstract class AbsFingerprintCallback implements OnFingerprintCallback {

    @Override
    public void onFail() {

    }

    @Override
    public void notSupport() {

    }

    @Override
    public void noFingerprint() {

    }
}
