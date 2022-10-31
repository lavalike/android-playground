package com.android.exercise.ui.activity.biometric;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.exercise.R;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.base.toolbar.ToolbarFactory;
import com.android.exercise.ui.activity.biometric.manager.FingerprintManager;
import com.android.exercise.ui.activity.biometric.manager.callback.AbsFingerprintCallback;
import com.wangzhen.commons.toolbar.impl.Toolbar;

public class BiometricActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biometric);
    }

    @Nullable
    @Override
    public Toolbar createToolbar() {
        return ToolbarFactory.themed(this, getString(R.string.item_biometric));
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_fingerprint:
                FingerprintManager.from(this).callback(new AbsFingerprintCallback() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(mContext, "认证成功", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void notSupport() {
                        Toast.makeText(mContext, "不支持指纹识别，快换个好手机", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void noFingerprint() {
                        Toast.makeText(mContext, "请先添加指纹", Toast.LENGTH_SHORT).show();
                    }
                }).auth();
                break;
        }
    }
}