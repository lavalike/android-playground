package com.android.exercise.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.BounceInterpolator;

import com.android.exercise.ui.widget.nineoldandroids.animation.ValueAnimator;

/**
 * Created by wangzhen on 2017/9/16.
 */
public class AnimView extends View {
    private final int PAINT_LINE_WIDTH = dip2px(4);
    private final int PAINT_ARC_WIDTH = dip2px(1);
    private Paint paintLine;
    private Paint paintArc;
    private Paint paintProgressLine;
    private float mArcStartAngle = -45;
    private RectF arcRectF;
    //扇形区域RectF宽度
    private int mArcRectWidth = 200;
    //内边距
    private int mPadding = 10;
    //进度条宽度
    private int mProgressLineWidth = 0;
    //文本区域高度
    private int mTextAreaHeight = 0;

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
                startProgressAnim();
                startTextAreaAnim();
            }
        }, 1000);
    }

    /**
     * 开始旋转动画
     */
    private void startRotateAnim() {
        ValueAnimator animator = ValueAnimator.ofFloat(mArcStartAngle, 0);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mArcStartAngle = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        animator.setDuration(800);
        animator.setInterpolator(new BounceInterpolator());
        animator.start();
    }

    /**
     * 开始进度动画
     */
    private void startProgressAnim() {
        ValueAnimator animator = ValueAnimator.ofInt(mProgressLineWidth, getScreenWidth() - 2 * mPadding);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mProgressLineWidth = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        animator.setDuration(1500);
        animator.setStartDelay(800);
        animator.start();
    }

    /**
     * 开始改变文本区域高度
     */
    private void startTextAreaAnim() {
        ValueAnimator animator = ValueAnimator.ofInt(0, dip2px(200));
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mTextAreaHeight = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        animator.setDuration(1000);
        animator.setStartDelay(2300);
        animator.setInterpolator(new BounceInterpolator());
        animator.start();
    }

    private void init() {
        paintLine = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintLine.setColor(Color.parseColor("#EEEEEE"));
        paintLine.setStrokeWidth(PAINT_LINE_WIDTH);
        paintLine.setStyle(Paint.Style.FILL);

        paintProgressLine = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintProgressLine.setColor(Color.GRAY);
        paintProgressLine.setStrokeWidth(PAINT_LINE_WIDTH);
        paintLine.setStyle(Paint.Style.FILL);

        paintArc = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintArc.setColor(Color.BLACK);
        paintArc.setStrokeWidth(PAINT_ARC_WIDTH);
        paintArc.setStyle(Paint.Style.FILL);

        arcRectF = new RectF();
        arcRectF.set(
                getScreenWidth() - mArcRectWidth - mPadding,
                mPadding,
                getScreenWidth() - mPadding,
                mPadding + mArcRectWidth
        );
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawArc(canvas);
        drawRectLine(canvas);
        drawProgressLine(canvas);
        drawTextArea(canvas);
    }

    /**
     * 画文本区域
     *
     * @param canvas
     */
    private void drawTextArea(Canvas canvas) {
        canvas.drawRect(
                mPadding,
                mArcRectWidth / 2 + mPadding + PAINT_LINE_WIDTH,
                getWidth() - mPadding,
                mArcRectWidth / 2 + mPadding + PAINT_LINE_WIDTH + mTextAreaHeight,
                paintLine);
    }

    /**
     * 画直线 从右到左
     *
     * @param canvas
     */
    private void drawProgressLine(Canvas canvas) {
        canvas.drawRect(
                getScreenWidth() - mProgressLineWidth - mPadding,
                mArcRectWidth / 2 + mPadding,
                getWidth() - mPadding,
                mArcRectWidth / 2 + mPadding + PAINT_LINE_WIDTH,
                paintProgressLine);
    }

    /**
     * 画扇形底部直线
     *
     * @param canvas
     */
    private void drawRectLine(Canvas canvas) {
        canvas.drawRect(
                mPadding,
                mArcRectWidth / 2 + mPadding,
                getWidth() - mPadding,
                mArcRectWidth / 2 + mPadding + PAINT_LINE_WIDTH,
                paintLine);
    }

    /**
     * 画扇形
     *
     * @param canvas
     */
    private void drawArc(Canvas canvas) {
        canvas.drawArc(
                arcRectF,
                mArcStartAngle,
                -90,
                true,
                paintArc
        );
    }

    private int getScreenWidth() {
        return getContext().getResources().getDisplayMetrics().widthPixels;
    }

    private int dip2px(float dipValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
