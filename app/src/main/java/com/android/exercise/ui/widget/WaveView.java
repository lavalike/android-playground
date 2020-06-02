package com.android.exercise.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.android.exercise.R;

/**
 * 波浪View
 * Created by wangzhen on 2017/8/16.
 */
public class WaveView extends View {
    /**
     * 振幅
     */
    private int A = dip2px(10);
    /**
     * 偏距
     */
    private int K = dip2px(10);
    /**
     * 初相
     */
    private float φ = 10;
    /**
     * 角速度
     */
    private double ω;

    private Paint mPaint;
    private Path mPath;

    public WaveView(Context context) {
        this(context, null);
    }

    public WaveView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setStrokeWidth(dip2px(1));
        mPath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawSin(canvas);
    }

    /**
     * 绘制正弦曲线
     *
     * @param canvas
     */
    private void drawSin(Canvas canvas) {
        φ -= 0.03;
        float y;
        mPath.reset();
        mPath.moveTo(0, 0);
        mPath.lineTo(0, K);
        for (float x = 0; x <= getWidth(); x += 20) {
            y = (float) (A * Math.sin(ω * x + φ) + K);
            mPath.lineTo(x, getHeight() - y);
        }
        mPath.lineTo(getWidth(), 0);
        mPath.lineTo(0, 0);
        mPath.close();
        canvas.drawPath(mPath, mPaint);
        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        ω = 2 * Math.PI / getWidth();
        K = getHeight() / 2;
    }

    private int dip2px(float dipValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
