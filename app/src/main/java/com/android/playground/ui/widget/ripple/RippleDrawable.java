package com.android.playground.ui.widget.ripple;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by wangzhen on 2017/4/10.
 */

public class RippleDrawable extends Drawable {

    private int mAlpha = 255;
    private int mRippleColor = 0;
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private float mRipplePointX = 0;
    private float mRipplePointY = 0;
    private float mRippleRadius = 200;

    public RippleDrawable() {
        //抗锯齿
        mPaint.setAntiAlias(true);
        //防抖动
        mPaint.setDither(true);
        setRippleColor(Color.RED);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        canvas.drawCircle(mRipplePointX, mRipplePointY, mRippleRadius, mPaint);
    }

    public void setRippleColor(int color) {
        mRippleColor = color;
        onColorOrAlphaChange();
    }

    @Override
    public void setAlpha(@IntRange(from = 0, to = 255) int alpha) {
        mAlpha = alpha;
        onColorOrAlphaChange();
    }

    @Override
    public int getAlpha() {
        return mAlpha;
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        if (mPaint.getColorFilter() != colorFilter) {
            mPaint.setColorFilter(colorFilter);
        }
        invalidateSelf();
    }

    @Override
    public int getOpacity() {
        int alpha = mPaint.getAlpha();
        if (alpha == 255) {
            return PixelFormat.OPAQUE;
        } else if (alpha == 0) {
            return PixelFormat.TRANSPARENT;
        } else {
            return PixelFormat.TRANSLUCENT;
        }
    }

    private void onColorOrAlphaChange() {
        mPaint.setColor(mRippleColor);
        //如果不是透明的
        if (mAlpha != 255) {
            int alpha = mPaint.getAlpha();
            int realAlpha = alpha * (mAlpha / 255);
            mPaint.setAlpha(realAlpha);
        }
        invalidateSelf();
    }

    public void onTouch(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                onTouchDown(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                onTouchMove(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_UP:
                onTouchUp(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_CANCEL:
                onTouchCancel(event.getX(), event.getY());
                break;
        }
    }

    private void onTouchCancel(float x, float y) {

    }

    private void onTouchUp(float x, float y) {

    }

    private void onTouchMove(float x, float y) {

    }

    private void onTouchDown(float x, float y) {
        mRipplePointX = x;
        mRipplePointY = y;
        mRippleRadius += 20;
        invalidateSelf();
    }

}
