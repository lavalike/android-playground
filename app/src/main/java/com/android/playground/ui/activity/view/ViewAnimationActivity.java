package com.android.playground.ui.activity.view;

import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

import com.android.playground.R;
import com.android.playground.base.BaseActivity;
import com.android.playground.base.toolbar.ToolbarFactory;
import com.android.playground.databinding.ActivityBasicAnimationBinding;
import com.wangzhen.commons.toolbar.impl.Toolbar;

public class ViewAnimationActivity extends BaseActivity {
    private ActivityBasicAnimationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((binding = ActivityBasicAnimationBinding.inflate(getLayoutInflater())).getRoot());
        onClick();
    }

    @Override
    public Toolbar createToolbar() {
        return ToolbarFactory.themed(this, getString(R.string.item_view_animation));
    }

    public void onClick() {
        binding.viewTranslate.setOnClickListener(v -> translate());
        binding.viewScale.setOnClickListener(v -> scale());
        binding.viewRotate.setOnClickListener(v -> rotate());
        binding.viewAlpha.setOnClickListener(v -> alpha());
        binding.viewTogether.setOnClickListener(v -> together());
    }

    private void together() {
        AnimationSet set = new AnimationSet(false);

        AlphaAnimation animationAlpha = new AlphaAnimation(1f, 0f);
        animationAlpha.setRepeatMode(Animation.REVERSE);
        animationAlpha.setRepeatCount(Animation.INFINITE);

        RotateAnimation animationRotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animationRotate.setRepeatCount(Animation.INFINITE);
        animationRotate.setInterpolator(new LinearInterpolator());

        ScaleAnimation animationScale = new ScaleAnimation(1f, 0.2f, 1f, 0.2f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animationScale.setRepeatMode(Animation.REVERSE);
        animationScale.setRepeatCount(Animation.INFINITE);

        TranslateAnimation animationTranslate = new TranslateAnimation(0, 500, 0, 0);
        animationTranslate.setRepeatMode(Animation.REVERSE);
        animationTranslate.setRepeatCount(Animation.INFINITE);

        set.addAnimation(animationAlpha);
        set.addAnimation(animationRotate);
        set.addAnimation(animationScale);
        set.addAnimation(animationTranslate);
        set.setDuration(1500);
        binding.viewTogether.startAnimation(set);
    }

    private void alpha() {
        AlphaAnimation animation = new AlphaAnimation(1f, 0f);
        animation.setDuration(1500);
        animation.setRepeatMode(Animation.REVERSE);
        animation.setRepeatCount(Animation.INFINITE);
        binding.viewAlpha.startAnimation(animation);
    }

    private void rotate() {
        RotateAnimation animation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(1500);
        animation.setRepeatCount(Animation.INFINITE);
        animation.setInterpolator(new LinearInterpolator());
        binding.viewRotate.startAnimation(animation);
    }

    private void scale() {
        ScaleAnimation animation = new ScaleAnimation(1f, 0.2f, 1f, 0.2f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(1500);
        animation.setRepeatMode(Animation.REVERSE);
        animation.setRepeatCount(Animation.INFINITE);
        binding.viewScale.startAnimation(animation);
    }

    private void translate() {
        TranslateAnimation animation = new TranslateAnimation(0, 500, 0, 0);
        animation.setDuration(1500);
        animation.setRepeatMode(Animation.REVERSE);
        animation.setRepeatCount(Animation.INFINITE);
        binding.viewTranslate.startAnimation(animation);
    }

    private int dip2px(float dipValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
