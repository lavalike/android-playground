package com.android.playground.ui.activity.view;

import android.os.Bundle;

import com.android.playground.R;
import com.android.playground.base.BaseActivity;
import com.android.playground.base.toolbar.ToolbarFactory;
import com.android.playground.databinding.ActivityStateButtonBinding;
import com.wangzhen.commons.toolbar.impl.Toolbar;

public class StateButtonActivity extends BaseActivity {
    private ActivityStateButtonBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((binding = ActivityStateButtonBinding.inflate(getLayoutInflater())).getRoot());
        setEvents();
    }

    @Override
    public Toolbar createToolbar() {
        return ToolbarFactory.themed(this, getString(R.string.item_state_button));
    }

    public void setEvents() {
        binding.btnReset.setOnClickListener(v -> binding.btnState.reset());
        binding.btnLoading.setOnClickListener(v -> binding.btnState.startLoading());
        binding.btnSuccess.setOnClickListener(v -> binding.btnState.success());
    }
}
