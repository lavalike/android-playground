package com.android.exercise.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;

import com.android.exercise.R;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.base.toolbar.ToolbarFactory;
import com.wangzhen.commons.toolbar.impl.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ViewAnimationActivity extends BaseActivity {

    @BindView(R.id.view_translate)
    FrameLayout viewTranslate;
    @BindView(R.id.view_scale)
    FrameLayout viewScale;
    @BindView(R.id.view_rotate)
    FrameLayout viewRotate;
    @BindView(R.id.view_alpha)
    FrameLayout viewAlpha;
    @BindView(R.id.view_together)
    FrameLayout viewTogether;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_animation);
        ButterKnife.bind(this);
    }

    @Override
    public Toolbar createToolbar() {
        return ToolbarFactory.themed(this, getString(R.string.item_view_animation));
    }

    @OnClick({R.id.view_translate, R.id.view_scale, R.id.view_rotate, R.id.view_alpha, R.id.view_together})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.view_translate:
                translate();
                break;
            case R.id.view_scale:
                scale();
                break;
            case R.id.view_rotate:
                rotate();
                break;
            case R.id.view_alpha:
                alpha();
                break;
            case R.id.view_together:
                together();
                break;
        }
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
        viewTogether.startAnimation(set);
    }

    private void alpha() {
        AlphaAnimation animation = new AlphaAnimation(1f, 0f);
        animation.setDuration(1500);
        animation.setRepeatMode(Animation.REVERSE);
        animation.setRepeatCount(Animation.INFINITE);
        viewAlpha.startAnimation(animation);
    }

    private void rotate() {
        RotateAnimation animation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(1500);
        animation.setRepeatCount(Animation.INFINITE);
        animation.setInterpolator(new LinearInterpolator());
        viewRotate.startAnimation(animation);
    }

    private void scale() {
        ScaleAnimation animation = new ScaleAnimation(1f, 0.2f, 1f, 0.2f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(1500);
        animation.setRepeatMode(Animation.REVERSE);
        animation.setRepeatCount(Animation.INFINITE);
        viewScale.startAnimation(animation);
    }

    private void translate() {
        TranslateAnimation animation = new TranslateAnimation(0, 500, 0, 0);
        animation.setDuration(1500);
        animation.setRepeatMode(Animation.REVERSE);
        animation.setRepeatCount(Animation.INFINITE);
        viewTranslate.startAnimation(animation);
    }

    private int dip2px(float dipValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
