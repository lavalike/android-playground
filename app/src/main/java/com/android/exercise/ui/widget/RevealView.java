package com.android.exercise.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;

import com.android.exercise.R;
import com.android.exercise.ui.widget.nineoldandroids.animation.ValueAnimator;

/**
 * 小冰图标动画
 * Created by wangzhen on 2017/10/11.
 */
public class RevealView extends View {
    private int minWidth = dip2px(50);
    private int minHeight = dip2px(50);
    private RectF bitmapRect;
    private Bitmap bitmap;
    private Paint mPaint;
    private float mRingWidth = 0;
    private float initialWidth;

    public RevealView(Context context) {
        this(context, null);
    }

    public RevealView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RevealView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    /**
     * 初始化
     *
     * @param attrs
     */
    private void init(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.RevealView);
        int pictureId = typedArray.getResourceId(R.styleable.RevealView_rl_src, R.mipmap.ic_launcher);
        int colorId = typedArray.getColor(R.styleable.RevealView_rl_mask_color, Color.WHITE);
        typedArray.recycle();
        bitmap = BitmapFactory.decodeResource(getResources(), pictureId);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(colorId);
    }

    /**
     * 开启动画
     */
    public void startRevealAnim() {
        ValueAnimator animator = ValueAnimator.ofFloat(initialWidth, 0);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mRingWidth = (float) animation.getAnimatedValue();
                if (mRingWidth > initialWidth * 2 / 3) {
                    Interpolator interpolator = animation.getInterpolator();
                    if (interpolator instanceof DecelerateInterpolator) {
                    } else {
                        animation.setInterpolator(new DecelerateInterpolator(2f));
                    }
                }
                if (mRingWidth == 0) {
                    mPaint.setColor(Color.TRANSPARENT);
                }
                invalidate();
            }
        });
        animator.setDuration(500);
        animator.setInterpolator(new AccelerateInterpolator(0.2f));
        animator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //将绘制操作保存到新图层，因为图像合成是很昂贵的操作，若用到硬件加速，这里将图像合成放在离屏缓存中进行
        int saveCount = canvas.saveLayer(bitmapRect, null, Canvas.ALL_SAVE_FLAG);
        //绘制bitmap
        canvas.drawBitmap(bitmap, null, bitmapRect, null);
        //绘制圆环
        mPaint.setStrokeWidth(mRingWidth);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() / 2 - mRingWidth / 2, mPaint);
        //还原画布
        canvas.restoreToCount(saveCount);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        bitmapRect = new RectF(0, 0, w, h);
        mRingWidth = w / 2;
        initialWidth = mRingWidth;
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
