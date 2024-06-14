package com.android.playground.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.android.playground.R;
import com.android.playground.ui.widget.nineoldandroids.animation.ValueAnimator;

/**
 * 页面切换动画
 * Created by wangzhen on 2018/3/12.
 */
public class PageSwitchView extends View {
    private int minWidth = 0;
    private int minHeight = 0;
    private Paint paint;
    private int centerX;
    private int centerY;
    private float radius;
    private int minRadius;
    private int maxRadius;
    private int duration;

    public PageSwitchView(Context context) {
        this(context, null);
    }

    public PageSwitchView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PageSwitchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PageSwitchView);
        int colorId = typedArray.getResourceId(R.styleable.PageSwitchView_psv_color, R.color.colorPrimary);
        duration = typedArray.getInteger(R.styleable.PageSwitchView_psv_duration, 300);
        typedArray.recycle();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(ContextCompat.getColor(context, colorId));
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(10);
    }

    public void setCenterXY(int x, int y) {
        this.centerX = x;
        this.centerY = y;
    }

    public void setRadiusRange(int from, int to) {
        minRadius = from;
        maxRadius = to;
    }

    public void start() {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(minRadius, maxRadius);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                radius = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.setDuration(duration);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(centerX, centerY, radius, paint);
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

    private int dip2px(float dipValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
