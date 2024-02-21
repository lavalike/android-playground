package com.android.exercise.ui.activity.view;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.android.exercise.R;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.base.toolbar.ToolbarFactory;
import com.android.exercise.databinding.ActivityFrameAnimationBinding;
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
