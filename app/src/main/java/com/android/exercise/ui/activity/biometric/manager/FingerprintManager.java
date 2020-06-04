package com.android.exercise.ui.activity.biometric.manager;

import android.content.Context;
import android.os.Build;

import androidx.core.hardware.fingerprint.FingerprintManagerCompat;

import com.android.exercise.ui.activity.biometric.manager.callback.Fingerprint;
import com.android.exercise.ui.activity.biometric.manager.callback.OnFingerprintCallback;
import com.android.exercise.ui.activity.biometric.manager.impl.FingerprintM;

/**
 * FingerprintManager
 * Created by wangzhen on 2020/6/4.
 */
public class FingerprintManager {
    private Context mContext;
    private OnFingerprintCallback mCallback;

    public FingerprintManager(Context context) {
        this.mContext = context;
    }

    public static FingerprintManager from(Context context) {
        return new FingerprintManager(context.getApplicationContext());
    }

    public FingerprintManager callback(OnFingerprintCallback callback) {
        this.mCallback = callback;
        return this;
    }

    private boolean isSupported() {
        //硬件是否支持指纹识别
        if (!FingerprintManagerCompat.from(mContext).isHardwareDetected()) {
            mCallback.notSupport();
            return false;
        }
        //是否已添加指纹
        if (!FingerprintManagerCompat.from(mContext).hasEnrolledFingerprints()) {
            mCallback.noFingerprint();
            return false;
        }
        return true;
    }

    public void auth() {
        Fingerprint fingerprint = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            fingerprint = new FingerprintM(mContext, mCallback);
        } else {
            if (mCallback != null) {
                mCallback.notSupport();
            }
        }
        if (fingerprint != null) {
            if (isSupported()) {
                fingerprint.authenticate();
            }
        }
    }
}
