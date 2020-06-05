package com.android.exercise.ui.activity.biometric.manager.impl;

import android.content.Context;
import android.os.Build;
import android.os.Handler;

import androidx.annotation.RequiresApi;
import androidx.core.hardware.fingerprint.FingerprintManagerCompat;
import androidx.core.os.CancellationSignal;

import com.android.exercise.ui.activity.biometric.manager.FingerprintDialog;
import com.android.exercise.ui.activity.biometric.manager.callback.Fingerprint;
import com.android.exercise.ui.activity.biometric.manager.callback.OnFingerprintCallback;
import com.android.exercise.ui.activity.biometric.manager.util.CipherHelper;

/**
 * FingerprintM
 * Created by wangzhen on 2020/6/4.
 */
@RequiresApi(Build.VERSION_CODES.M)
public class FingerprintM implements Fingerprint {
    private FingerprintManagerCompat.CryptoObject crypto = null;
    private Context mContext;
    private OnFingerprintCallback mCallback;
    private FingerprintDialog mDialog;
    private CancellationSignal cancellationSignal;

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
        mDialog = new FingerprintDialog(mContext);
        mDialog.setOnDismissListener(dialog -> {
            cancellationSignal.cancel();
        });
        mDialog.show();

        cancellationSignal = new CancellationSignal();
        cancellationSignal.setOnCancelListener(() -> {
            mDialog.dismiss();
        });
        FingerprintManagerCompat fingerprintManagerCompat = FingerprintManagerCompat.from(mContext);
        fingerprintManagerCompat.authenticate(crypto, 0, cancellationSignal, authenticationCallback, null);
    }

    private FingerprintManagerCompat.AuthenticationCallback authenticationCallback = new FingerprintManagerCompat.AuthenticationCallback() {
        @Override
        public void onAuthenticationError(int errMsgId, CharSequence errString) {
            super.onAuthenticationError(errMsgId, errString);
            if (errMsgId != 5) {
                String msg = errString.toString();
                if (errMsgId == 7) {
                    msg = "失败次数过多，请稍候再试";
                }
                if (mDialog != null) {
                    mDialog.setError(msg);
                }
            }
        }

        @Override
        public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
            super.onAuthenticationHelp(helpMsgId, helpString);
            if (mDialog != null) {
                mDialog.setError(helpString.toString());
            }
        }

        @Override
        public void onAuthenticationSucceeded(FingerprintManagerCompat.AuthenticationResult result) {
            super.onAuthenticationSucceeded(result);
            if (mDialog != null) {
                mDialog.setSuccess("认证成功");
                new Handler().postDelayed(() -> mDialog.dismiss(), 1000);
            }
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
