package com.android.exercise.ui.widget;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class StateProgress extends View {
    private int minWidth = dip2px(50);
    private int minHeight = dip2px(50);
    private Paint paint;
    private float strokeWidth = dip2px(1f);
    private int paintColor = Color.BLACK;
    private float mRadius;
    private int mWidth;
    private int mHeight;
    private RectF arcRectF;
    private StateEnum mState = StateEnum.Success;
    private int startAngle = -90;
    private int minAngle = -90;
    private int sweepAngle = 0;
    private int curAngle = 0;
    private float circleProgress = 0;
    private float checkMarkProgress = 0;
    private Path pathCircle;
    private Path pathCheckMark;
    //截取PathMeasure中的path
    private Path mPathCircleDst;
    private PathMeasure pathMeasure;

    public StateProgress(Context context) {
        super(context);
        init();
    }

    public StateProgress(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public StateProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(paintColor);
        paint.setDither(true);
        paint.setStrokeWidth(strokeWidth);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);

        pathCircle = new Path();
        pathCheckMark = new Path();
        mPathCircleDst = new Path();
        pathMeasure = new PathMeasure();
    }

    public void setStrokeWidth(float strokeWidth) {
        this.strokeWidth = strokeWidth;
        paint.setStrokeWidth(strokeWidth);
    }

    public void setPaintColor(int paintColor) {
        this.paintColor = paintColor;
        paint.setColor(paintColor);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        mRadius = mWidth / 2f - strokeWidth;
        arcRectF = new RectF(strokeWidth, strokeWidth, mWidth - strokeWidth, mHeight - strokeWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.translate(getPaddingLeft(), getPaddingTop());
        if (mState == StateEnum.Loading) {
            drawArc(canvas);
        }
        if (mState == StateEnum.Success) {
            drawCheckMark(canvas);
        }
    }

    private void drawCheckMark(Canvas canvas) {
        pathCircle.reset();
        pathCircle.addCircle(mWidth / 2, mHeight / 2, mRadius, Path.Direction.CW);
        pathMeasure.setPath(pathCircle, true);
        pathMeasure.getSegment(0, circleProgress * pathMeasure.getLength(), mPathCircleDst, true);
        canvas.drawPath(mPathCircleDst, paint);
        if (circleProgress == 1) {
            float x = mWidth * 1f / 20 * 6;
            float y = mWidth * 1f / 20 * 10;
            pathCheckMark.moveTo(x, y);

            x = mWidth * 1f / 20 * 9;
            y = mWidth * 1f / 20 * 13;
            pathCheckMark.lineTo(x, y);

            x = mWidth * 1f / 20 * 14;
            y = mWidth * 1f / 20 * 7;
            pathCheckMark.lineTo(x, y);

            pathMeasure.nextContour();
            pathMeasure.setPath(pathCheckMark, false);
            pathMeasure.getSegment(0, checkMarkProgress * pathMeasure.getLength(), mPathCircleDst, true);
            canvas.drawPath(mPathCircleDst, paint);
        }

    }

    private void drawArc(Canvas canvas) {
        if (startAngle == minAngle) {
            sweepAngle += 6;
        }
        if (sweepAngle >= 300 || startAngle > minAngle) {
            startAngle += 6;
            if (sweepAngle > 20) {
                sweepAngle -= 6;
            }
        }
        if (startAngle > minAngle + 300) {
            startAngle %= 360;
            minAngle = startAngle;
            sweepAngle = 20;
        }
        canvas.rotate(curAngle += 4, mWidth / 2, mHeight / 2);
        canvas.drawArc(arcRectF, startAngle, sweepAngle, false, paint);
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }

    /**
     * 测量高度
     *
     * @param heightMeasureSpec
     * @return
     */
    private int measureHeight(int heightMeasureSpec) {
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int height = 0;
        switch (heightMode) {
            case MeasureSpec.EXACTLY:
                height = heightSize;
                break;
            case MeasureSpec.AT_MOST:
                height = Math.min(minHeight, heightSize);
                break;
            case MeasureSpec.UNSPECIFIED:
                height = minHeight;
                break;
        }
        return MeasureSpec.makeMeasureSpec(height, heightMode);
    }

    /**
     * 测量宽度
     *
     * @param widthMeasureSpec
     * @return
     */
    private int measureWidth(int widthMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int width = 0;
        switch (widthMode) {
            case MeasureSpec.EXACTLY:
                width = widthSize;
                break;
            case MeasureSpec.AT_MOST:
                width = Math.min(minWidth, widthSize);
                break;
            case MeasureSpec.UNSPECIFIED:
                width = minWidth;
                break;
        }
        return MeasureSpec.makeMeasureSpec(width, widthMode);
    }

    public void startLoading() {
        reset();
        mState = StateEnum.Loading;
        invalidate();
    }

    public void success() {
        reset();
        mState = StateEnum.Success;
        ValueAnimator circleAnimator = ValueAnimator.ofFloat(0f, 1f);
        circleAnimator.addUpdateListener(new android.animation.ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(android.animation.ValueAnimator animation) {
                circleProgress = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        ValueAnimator checkMarkAnimator = ValueAnimator.ofFloat(0f, 1f);
        checkMarkAnimator.addUpdateListener(new android.animation.ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(android.animation.ValueAnimator animation) {
                checkMarkProgress = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        AnimatorSet set = new AnimatorSet();
        set.play(checkMarkAnimator).after(circleAnimator);
        set.setDuration(500);
        set.start();
    }

    public void reset() {
        startAngle = -90;
        minAngle = -90;
        sweepAngle = 0;
        curAngle = 0;

        circleProgress = 0;
        checkMarkProgress = 0;
        pathCircle.reset();
        pathCheckMark.reset();
        mPathCircleDst.reset();
    }

    private int dip2px(float dipValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public enum StateEnum {
        Loading,
        Success
    }
}