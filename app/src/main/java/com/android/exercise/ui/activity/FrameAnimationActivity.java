package com.android.exercise.ui.activity;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import android.view.View;

import com.android.exercise.R;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.base.toolbar.ToolBarCommonHolder;

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

    @Override
    protected void onSetupToolbar(Toolbar toolbar, ActionBar actionBar) {
        new ToolBarCommonHolder(this, toolbar, getString(R.string.item_frame_animation));
    }
}
