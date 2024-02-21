package com.android.exercise.ui.activity.view;

import android.os.Bundle;
import android.os.Handler;

import com.android.exercise.R;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.base.toolbar.ToolbarFactory;
import com.android.exercise.databinding.ActivityPorterDuffBinding;
import com.wangzhen.commons.toolbar.impl.Toolbar;

public class PorterDuffActivity extends BaseActivity {
    private ActivityPorterDuffBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((binding = ActivityPorterDuffBinding.inflate(getLayoutInflater())).getRoot());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                binding.revealView.startRevealAnim();
            }
        }, 1000);
    }

    @Override
    public Toolbar createToolbar() {
        return ToolbarFactory.themed(this, getString(R.string.item_porter_duff));
    }
}
