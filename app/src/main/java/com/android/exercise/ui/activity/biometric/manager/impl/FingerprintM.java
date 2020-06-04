package com.android.exercise.ui.activity.biometric.manager.impl;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.hardware.fingerprint.FingerprintManagerCompat;
import androidx.core.os.CancellationSignal;

import com.android.exercise.ui.activity.biometric.manager.CipherHelper;
import com.android.exercise.ui.activity.biometric.manager.callback.Fingerprint;
import com.android.exercise.ui.activity.biometric.manager.callback.OnFingerprintCallback;

/**
 * FingerprintM
 * Created by wangzhen on 2020/6/4.
 */
@RequiresApi(Build.VERSION_CODES.M)
public class FingerprintM implements Fingerprint {
    private FingerprintManagerCompat.CryptoObject crypto = null;
    private Context mContext;
    private OnFingerprintCallback mCallback;

    public FingerprintM(Context context, OnFingerprintCallback callback) {
        this.mContext = context;
        this.mCallback = callback;
        try {
            crypto = new FingerprintManagerCompat.CryptoObject(new CipherHelper().createCipher());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void authenticate() {
        FingerprintManagerCompat fingerprintManagerCompat = FingerprintManagerCompat.from(mContext);
        CancellationSignal cancellationSignal = new CancellationSignal();
        fingerprintManagerCompat.authenticate(crypto, 0, cancellationSignal, authenticationCallback, null);
    }

    private FingerprintManagerCompat.AuthenticationCallback authenticationCallback = new FingerprintManagerCompat.AuthenticationCallback() {
        @Override
        public void onAuthenticationError(int errMsgId, CharSequence errString) {
            super.onAuthenticationError(errMsgId, errString);
            if (mCallback != null) {
                mCallback.onError(errString.toString());
            }
        }

        @Override
        public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
            super.onAuthenticationHelp(helpMsgId, helpString);
            if (mCallback != null) {
                mCallback.onError(helpString.toString());
            }
        }

        @Override
        public void onAuthenticationSucceeded(FingerprintManagerCompat.AuthenticationResult result) {
            super.onAuthenticationSucceeded(result);
            if (mCallback != null) {
                mCallback.onSuccess();
            }
        }

        @Override
        public void onAuthenticationFailed() {
            super.onAuthenticationFailed();
            if (mCallback != null) {
                mCallback.onError("认证失败");
            }
        }
    };
}
