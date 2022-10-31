package com.android.exercise.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;

import androidx.annotation.Nullable;

import com.android.exercise.R;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.base.toolbar.ToolbarFactory;
import com.wangzhen.commons.toolbar.impl.Toolbar;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class AutoServiceActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_service);
        ButterKnife.bind(this);
    }

    @Nullable
    @Override
    public Toolbar createToolbar() {
        return ToolbarFactory.themed(this, getString(R.string.item_accessibility));
    }

    @OnClick({R.id.btn_openService, R.id.btn_test})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_openService:
                Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                startActivity(intent);
                break;
            case R.id.btn_test:
                showToast("点击:" + System.currentTimeMillis());
                break;
        }
    }
}
