package com.android.exercise.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;

import com.android.exercise.R;
import com.android.exercise.ui.widget.nineoldandroids.animation.ValueAnimator;

/**
 * 加载View
 * Created by wangzhen on 2017/9/25.
 */
public class LoadingView extends View {
    private int minWidth = dip2px(50);
    private int minHeight = dip2px(50);
    private PorterDuffXfermode mXfermode;
    private Paint mPaint;
    private Bitmap bitmap;
    private RectF bitmapRect;
    private RectF rectF = new RectF();
    private int maskWidth = dip2px(50);//默认遮罩宽度
    private float percent = 0;//变化比例

    public LoadingView(Context context) {
        this(context, null);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
        startAnim();
    }

    private void init(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.LoadingView);
        int resourceId = typedArray.getResourceId(R.styleable.LoadingView_lv_background_image, R.mipmap.ic_launcher);
        int maskColor = typedArray.getColor(R.styleable.LoadingView_lv_mask_color, Color.GRAY);
        maskWidth = typedArray.getDimensionPixelSize(R.styleable.LoadingView_lv_mask_width, maskWidth);
        typedArray.recycle();

        bitmap = BitmapFactory.decodeResource(getResources(), resourceId);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(maskColor);
        mPaint.setStyle(Paint.Style.FILL);
        //设置混合模式
        mXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP);
        mPaint.setXfermode(mXfermode);
    }

    private void startAnim() {
        ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                percent = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        animator.setRepeatCount(Animation.INFINITE);
        animator.setDuration(1000);
        animator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //将绘制操作保存到新图层，因为图像合成是很昂贵的操作，若用到硬件加速，这里将图像合成放在离屏缓存中进行
        int saveCount = canvas.saveLayer(bitmapRect, mPaint, Canvas.ALL_SAVE_FLAG);
        //绘制图像
        canvas.drawBitmap(bitmap, null, bitmapRect, null);
        //绘制矩形
        rectF.set(
                percent * getWidth() * 2 - getWidth(),
                0,
                percent * getWidth() * 2 - getWidth() + maskWidth,
                getHeight()
        );
        //歪斜
        canvas.skew(0.5f, 0);
        canvas.drawRect(rectF, mPaint);
        //还原画布
        canvas.restoreToCount(saveCount);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        bitmapRect = new RectF(0, 0, w, h);
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
