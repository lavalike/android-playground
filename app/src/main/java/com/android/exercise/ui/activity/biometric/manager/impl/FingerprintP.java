package com.android.exercise.ui.activity.biometric.manager.impl;

import android.content.Context;
import android.hardware.biometrics.BiometricPrompt;
import android.os.Build;
import android.os.CancellationSignal;

import androidx.annotation.RequiresApi;

import com.android.exercise.ui.activity.biometric.manager.util.CipherHelper;
import com.android.exercise.ui.activity.biometric.manager.callback.Fingerprint;
import com.android.exercise.ui.activity.biometric.manager.callback.OnFingerprintCallback;

/**
 * FingerprintP
 * Created by wangzhen on 2020/6/5.
 */
@RequiresApi(Build.VERSION_CODES.P)
public class FingerprintP implements Fingerprint {
    private Context mContext;
    private OnFingerprintCallback mCallback;
    private BiometricPrompt.CryptoObject crypto;

    public FingerprintP(Context context, OnFingerprintCallback callback) {
        this.mContext = context;
        this.mCallback = callback;
        try {
            crypto = new BiometricPrompt.CryptoObject(new CipherHelper().createCipher());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void authenticate() {
        BiometricPrompt biometricPrompt = new BiometricPrompt.Builder(mContext)
                .setTitle("指纹认证")
                .setDescription("测试用")
                .build();
        CancellationSignal cancellationSignal = new CancellationSignal();
        biometricPrompt.authenticate(crypto, cancellationSignal, mContext.getMainExecutor(), authenticationCallback);
    }

    private BiometricPrompt.AuthenticationCallback authenticationCallback = new BiometricPrompt.AuthenticationCallback() {
        @Override
        public void onAuthenticationError(int errMsgId, CharSequence errString) {
            super.onAuthenticationError(errMsgId, errString);
            if (mCallback != null) {
                mCallback.onFail();
            }
        }

        @Override
        public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
            super.onAuthenticationHelp(helpMsgId, helpString);
            if (mCallback != null) {
                mCallback.onFail();
            }
        }


        @Override
        public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
            super.onAuthenticationSucceeded(result);
            if (mCallback != null) {
                mCallback.onSuccess();
            }
        }

        @Override
        public void onAuthenticationFailed() {
            super.onAuthenticationFailed();
            if (mCallback != null) {
                mCallback.onFail();
            }
        }
    };
}
