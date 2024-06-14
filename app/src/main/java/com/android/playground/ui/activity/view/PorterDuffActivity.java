package com.android.playground.ui.activity.view;

import android.os.Bundle;
import android.os.Handler;

import com.android.playground.R;
import com.android.playground.base.BaseActivity;
import com.android.playground.base.toolbar.ToolbarFactory;
import com.android.playground.databinding.ActivityPorterDuffBinding;
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
