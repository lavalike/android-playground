package com.android.exercise.ui.activity.view;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.android.exercise.R;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.base.toolbar.ToolbarFactory;
import com.wangzhen.commons.toolbar.impl.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FrameAnimationActivity extends BaseActivity {

    @BindView(R.id.view_frame)
    View viewFrame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame_animation);
        ButterKnife.bind(this);

        Drawable background = viewFrame.getBackground();
        if (background instanceof AnimationDrawable) {
            AnimationDrawable anim = (AnimationDrawable) background;
            anim.start();
        }
    }

    @Nullable
    @Override
    public Toolbar createToolbar() {
        return ToolbarFactory.themed(this, getString(R.string.item_frame_animation));
    }
}
