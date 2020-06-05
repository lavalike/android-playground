package com.android.exercise.ui.activity.biometric;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import com.android.exercise.R;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.base.toolbar.ToolBarCommonHolder;
import com.android.exercise.ui.activity.biometric.manager.FingerprintManager;
import com.android.exercise.ui.activity.biometric.manager.callback.AbsFingerprintCallback;

public class BiometricActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biometric);
    }

    @Override
    protected void onSetupToolbar(Toolbar toolbar, ActionBar actionBar) {
        new ToolBarCommonHolder(this, toolbar, getString(R.string.item_biometric));
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_fingerprint:
                if (FingerprintManager.from(this).isSupported()) {
                    FingerprintManager.from(this).callback(new AbsFingerprintCallback() {
                        @Override
                        public void onSuccess() {
                            Toast.makeText(mContext, "认证成功", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void noFingerprint() {
                            Toast.makeText(mContext, "请先添加指纹", Toast.LENGTH_SHORT).show();
                        }
                    }).auth();
                } else {
                    Toast.makeText(mContext, "不支持指纹识别，换手机吧", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}