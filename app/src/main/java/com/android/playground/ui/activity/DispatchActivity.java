package com.android.playground.ui.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.android.playground.R;
import com.android.playground.base.BaseActivity;
import com.android.playground.base.toolbar.ToolbarFactory;
import com.android.playground.databinding.ActivityDispatchBinding;
import com.wangzhen.commons.toolbar.impl.Toolbar;

/**
 * 事件分发
 */
public class DispatchActivity extends BaseActivity {
    private ActivityDispatchBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((binding = ActivityDispatchBinding.inflate(getLayoutInflater())).getRoot());
        setEvents();
    }

    @Nullable
    @Override
    public Toolbar createToolbar() {
        return ToolbarFactory.themed(this, getString(R.string.item_dispatch));
    }

    public void setEvents() {
        binding.btnDispatch.setOnClickListener(view -> showToast("按钮被点击"));
        binding.relativelayoutDispatch.setOnClickListener(view -> showToast("relativelayout被点击"));
    }
}
