package com.android.playground.ui.activity.view;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.android.playground.R;
import com.android.playground.base.BaseActivity;
import com.android.playground.base.toolbar.ToolbarFactory;
import com.android.playground.databinding.ActivityFrameAnimationBinding;
import com.wangzhen.commons.toolbar.impl.Toolbar;

public class FrameAnimationActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityFrameAnimationBinding binding;
        setContentView((binding = ActivityFrameAnimationBinding.inflate(getLayoutInflater())).getRoot());

        Drawable background = binding.viewFrame.getBackground();
        if (background instanceof AnimationDrawable anim) {
            anim.start();
        }
    }

    @Nullable
    @Override
    public Toolbar createToolbar() {
        return ToolbarFactory.themed(this, getString(R.string.item_frame_animation));
    }
}
