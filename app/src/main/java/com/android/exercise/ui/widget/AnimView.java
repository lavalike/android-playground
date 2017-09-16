package com.android.exercise.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.android.exercise.ui.widget.nineoldandroids.animation.ValueAnimator;

/**
 * Created by wangzhen on 2017/9/16.
 */
public class AnimView extends View {
    private Paint paint;
    private PathMeasure pathMeasure;
    private float mRotateAngle;

    public AnimView(Context context) {
        this(context, null);
    }

    public AnimView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AnimView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startRotateAnim();
            }
        }, 1000);
    }

    /**
     * 开始旋转动画
     */
    private void startRotateAnim() {
        ValueAnimator animator = ValueAnimator.ofFloat(0, 45);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mRotateAngle = (float) animation.getAnimatedValue();
                Log.e("TAG", "旋转角度：" + mRotateAngle);
                invalidate();
            }
        });
        animator.setDuration(1000);
        animator.start();
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(dip2px(1));
        paint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawTriangle(canvas);
    }

    /**
     * 画三角形
     *
     * @param canvas
     */
    private void drawTriangle(Canvas canvas) {
        canvas.rotate(mRotateAngle);
        Path path = new Path();
        path.moveTo(getScreenWidth() - 120, 10);
        path.lineTo(getScreenWidth() - 10, 10);
        path.lineTo(getScreenWidth() - 65, 70);
        path.lineTo(getScreenWidth() - 120, 10);
        path.close();
        canvas.drawPath(path, paint);
    }

    private int getScreenWidth() {
        return getContext().getResources().getDisplayMetrics().widthPixels;
    }

    private int dip2px(float dipValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
