package com.android.playground.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;

import androidx.annotation.Nullable;

import com.android.playground.R;
import com.android.playground.base.BaseActivity;
import com.android.playground.base.toolbar.ToolbarFactory;
import com.android.playground.databinding.ActivityAutoServiceBinding;
import com.wangzhen.commons.toolbar.impl.Toolbar;

public class AutoServiceActivity extends BaseActivity {
    private ActivityAutoServiceBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((binding = ActivityAutoServiceBinding.inflate(getLayoutInflater())).getRoot());
        setEvents();
    }

    @Nullable
    @Override
    public Toolbar createToolbar() {
        return ToolbarFactory.themed(this, getString(R.string.item_accessibility));
    }

    public void setEvents() {
        binding.btnOpenService.setOnClickListener(view -> {
            Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
            startActivity(intent);
        });
        binding.btnTest.setOnClickListener(view -> {
            showToast("点击:" + System.currentTimeMillis());
        });
    }
}
