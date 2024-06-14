package com.android.playground.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;

import com.android.playground.R;
import com.android.playground.ui.widget.nineoldandroids.animation.ValueAnimator;

/**
 * VoiceWaveView
 * Created by wangzhen on 2020/3/23.
 */
public class VoiceWaveView extends View {
    private static final long DURATION = 1500;
    private Paint mPaint;
    private Path mPath;
    private int mHeight;
    private int mWidth;

    private int mWaveCount = 0;
    private int mCenterY = 0;

    private float mWaveBit = 1 / 4f;
    private int mWaveLength = 700;
    private int mWaveHeight = 0;
    private int mOffset = 0;
    private int mWaveColor;


    public VoiceWaveView(Context context) {
        this(context, null);
    }

    public VoiceWaveView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VoiceWaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
        start();
    }

    private void init(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.VoiceWaveView);
        mWaveColor = typedArray.getColor(R.styleable.VoiceWaveView_wave_color, getColor(R.color.color_61cc5f_20));
        typedArray.recycle();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(mWaveColor);
        mPath = new Path();
    }

    private int getColor(int color) {
        return getContext().getResources().getColor(color);
    }

    private void start() {
        ValueAnimator animator = ValueAnimator.ofInt(0, mWaveLength);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mOffset = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        animator.setDuration(DURATION);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.start();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mCenterY = mHeight / 3;
        mWaveHeight = 100;
        mWaveCount = (int) Math.round(mWidth * 1f / mWaveLength + 1.5);
        canvas.drawPath(createPath(mWaveLength, mWaveHeight, mWaveCount, mOffset, mCenterY), mPaint);

        mCenterY = mHeight / 2;
        mWaveHeight = 60;
        mWaveCount = (int) Math.round(mWidth * 1f / mWaveLength + 1.5);
        mOffset -= 200;
        canvas.drawPath(createPath(mWaveLength, mWaveHeight, mWaveCount, mOffset, mCenterY), mPaint);

        mWaveHeight = 20;
        canvas.drawPath(createPath(mWaveLength, mWaveHeight, mWaveCount, -mOffset, mCenterY), mPaint);
    }

    private Path createPath(int waveLength, int waveHeight, int waveCount, int offset, int center) {
        mPath.reset();
        mPath.moveTo(-waveLength + offset, center);
        for (int i = 0; i < waveCount; i++) {
            mPath.quadTo((-waveLength * (1 - mWaveBit)) + (i * waveLength) + offset, center + waveHeight, (-waveLength * 1f / 2) + (i * waveLength) + offset, center);
            mPath.quadTo((-waveLength * mWaveBit) + (i * waveLength) + offset, center - waveHeight, i * waveLength + offset, center);
        }
        mPath.lineTo(mWidth * 2, mHeight);
        mPath.lineTo(0, mHeight);
        mPath.close();
        return mPath;
    }
}
