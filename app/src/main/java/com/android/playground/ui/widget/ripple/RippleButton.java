package com.android.playground.ui.widget.ripple;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;

/**
 * Created by wangzhen on 2017/4/10.
 */

public class RippleButton extends AppCompatButton {

    private RippleDrawable mRippleDrawable;

    public RippleButton(Context context) {
        this(context, null);
    }

    public RippleButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RippleButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mRippleDrawable = new RippleDrawable();
        mRippleDrawable.setCallback(this);
        setBackgroundDrawable(mRippleDrawable);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mRippleDrawable.draw(canvas);
    }

    @Override
    protected boolean verifyDrawable(@NonNull Drawable who) {
        return who == mRippleDrawable || super.verifyDrawable(who);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mRippleDrawable.onTouch(event);
        return super.onTouchEvent(event);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mRippleDrawable.setBounds(0, 0, getWidth(), getHeight());
    }
}
