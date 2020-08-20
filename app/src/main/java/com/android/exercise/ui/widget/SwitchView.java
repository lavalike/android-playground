package com.android.exercise.ui.widget;

import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Property;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import androidx.core.content.ContextCompat;

/**
 * SwitchView
 * Created by wangzhen on 2020/8/20.
 */
public class SwitchView extends View {
    private int width;
    private int height;
    // 按钮直径
    private float mKnobD;
    // 按钮距离边框距离
    private float mInnerPadding;
    // 外边框
    private float mOuterStrokeWidth;
    // 左右两边半径
    private float mEdgeRadius;

    private Paint paint;
    // 按钮圆心
    private PointF mKnobCircleCenter;
    private PointF mLeftCircleCenter;
    private PointF mRightCircleCenter;

    private RectF mBgRect;
    private RectF mOuterRect;

    private RectF knobBound;
    //  拓长的距离
    private float knobMaxExpand;

    // 外框颜色
    private int mOuterStrokeColor;
    private int mBgColor;
    private int mKnobOpenedColor;
    private int mKnobClosedColor;
    private int mKnobColor;

    private boolean isOn;
    private boolean preIsOn;
    private float progress;
    private float knobExpandRate = 0f; // [0f, 1f]

    private static final long commonDuration = 300L;

    private OnSwitchStateChangeListener onSwitchStateChangeListener;

    // 按钮移动动画
    private ObjectAnimator knobMoveAnimator;
    private Property<SwitchView, Float> knobMoveProperty =
            new Property<SwitchView, Float>(Float.class, "knobMove") {
                @Override
                public void set(SwitchView sv, Float knobExpandRate) {
                    sv.setProgress(knobExpandRate, true);
                }

                @Override
                public Float get(SwitchView sv) {
                    return sv.getProgress();
                }
            };

    // 按钮扩大动画
    private ObjectAnimator knobExpandAnimator;
    private Property<SwitchView, Float> knobExpandProperty =
            new Property<SwitchView, Float>(
                    Float.class, "knobExpand") {
                @Override
                public void set(SwitchView sv, Float knobExpandRate) {
                    sv.setKnobExpandRate(knobExpandRate);
                }

                @Override
                public Float get(SwitchView sv) {
                    return sv.getKnobExpandRate();
                }
            };

    private GestureDetector gestureDetector;
    private GestureDetector.SimpleOnGestureListener gestureListener = new GestureDetector
            .SimpleOnGestureListener() {
        @Override
        public boolean onDown(MotionEvent event) {
            return true;
        }

        @Override
        public void onShowPress(MotionEvent event) {
            knobExpandAnimator.setFloatValues(knobExpandRate, 1.0F);
            knobExpandAnimator.start();
        }

        @Override
        public boolean onSingleTapUp(MotionEvent event) {
            if (preIsOn == isOn) { // 可以切换
                switchState(true);
            }
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2,
                                float distanceX, float distanceY) {
            if (e2.getX() > (mLeftCircleCenter.x + mRightCircleCenter.x) / 2) { // 开
                if (!preIsOn && progress < 1f) {
                    preIsOn = true;
                    handleAnimator();
                }
            } else { //  关
                if (preIsOn && progress > 0f) {
                    preIsOn = false;
                    handleAnimator();
                }
            }

            return true;
        }
    };

    public SwitchView(Context context) {
        super(context);
        init(context, null);

    }

    public SwitchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);

    }

    public SwitchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SwitchView(Context context, AttributeSet attrs, int defStyleAttr, int
            defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

        mOuterStrokeWidth = dp2px(0.5f);
        mInnerPadding = dp2px(2f);

        mKnobCircleCenter = new PointF();
        mLeftCircleCenter = new PointF();
        mRightCircleCenter = new PointF();
        mBgRect = new RectF();
        mOuterRect = new RectF();
        knobBound = new RectF();
        progress = isOn ? 1 : 0;

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        gestureDetector = new GestureDetector(context, gestureListener);
        gestureDetector.setIsLongpressEnabled(false);

        initColor();

        setWillNotDraw(false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

        knobMoveAnimator = ObjectAnimator.ofFloat(this, knobMoveProperty, progress, isOn ? 1f :
                0f);
        knobMoveAnimator.setDuration(commonDuration);
        knobMoveAnimator.setInterpolator(new DecelerateInterpolator());

        knobExpandAnimator = ObjectAnimator.ofFloat(this, knobExpandProperty, knobExpandRate, 1.0F);
        knobExpandAnimator.setDuration(commonDuration);
        knobExpandAnimator.setInterpolator(new DecelerateInterpolator());

    }

    /**
     * 设置状态, 默认没有过度动画
     *
     * @param on 开启或关闭
     */
    public void setState(boolean on) {
        setState(on, false);
    }

    /**
     * 设置状态
     *
     * @param on       开启或关闭
     * @param showAnim 是否有过度动画
     */
    public void setState(boolean on, boolean showAnim) {
        if (isOn != on) {
            switchState(showAnim);
        }
    }

    public boolean getState() {
        return this.isOn;
    }

    /**
     * 切换状态
     *
     * @param showAnim 是否有过度动画
     */
    public void switchState(boolean showAnim) {
        isOn = preIsOn = !isOn;
        if (showAnim) {
            handleAnimator();
        } else {
            setProgress(isOn ? 1 : 0, true);
        }
        if (onSwitchStateChangeListener != null) {
            onSwitchStateChangeListener.onSwitchStateChange(SwitchView.this, isOn);
        }
    }

    /**
     * 初始化主题相关颜色
     */
    private void initColor() {
        mBgColor = ContextCompat.getColor(getContext(), android.R.color.white);
        mKnobClosedColor = Color.parseColor("#9d9d9d");
        mKnobOpenedColor = Color.parseColor("#f18e1a");
        mOuterStrokeColor = Color.parseColor("#d8d8d8");
    }

    private void handleAnimator() {

        knobMoveAnimator.setFloatValues(progress, preIsOn ? 1f : 0f);
        knobMoveAnimator.start();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                knobExpandAnimator.setFloatValues(knobExpandRate, 0.0F);
                knobExpandAnimator.start();
                if (isOn != preIsOn) {
                    isOn = preIsOn;
                    if (onSwitchStateChangeListener != null) {
                        onSwitchStateChangeListener.onSwitchStateChange(this, isOn);
                    }
                }
                break;
        }

        return gestureDetector.onTouchEvent(event);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if (changed) {
            width = right - left;
            height = bottom - top;
            mEdgeRadius = (height - getPaddingTop() - getPaddingBottom()) / 2f;
            mKnobD = height - getPaddingTop() - getPaddingBottom() - mOuterStrokeWidth * 2 -
                    mInnerPadding * 2;

            mLeftCircleCenter.set(height / 2f, height / 2f);
            mRightCircleCenter.set(width - height / 2f, height / 2f);
            mKnobCircleCenter.y = height / 2f;

            mOuterRect.set(getPaddingLeft(), getPaddingTop(),
                    width - getPaddingRight(), height - getPaddingBottom());
            mBgRect.set(getPaddingLeft() + mOuterStrokeWidth / 2,
                    getPaddingTop() + mOuterStrokeWidth / 2,
                    width - getPaddingRight() - mOuterStrokeWidth / 2,
                    height - getPaddingBottom() - mOuterStrokeWidth / 2);

            knobMaxExpand = Math.min(width * 0.7F, mKnobD * 1.25F) - mKnobD;

            if (knobMaxExpand < 0) {
                knobMaxExpand = 0f;
            }

            setProgress(progress, false);

        }
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 画背景 左半圆，中矩形，右半圆
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(mBgColor);

        canvas.drawRoundRect(mBgRect, mEdgeRadius - mOuterStrokeWidth,
                mEdgeRadius - mOuterStrokeWidth, paint);
        // 画边框
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(mOuterStrokeWidth);
        paint.setColor(mOuterStrokeColor);
        canvas.drawRoundRect(mBgRect, mEdgeRadius - mOuterStrokeWidth, mEdgeRadius -
                mOuterStrokeWidth, paint);

        // 画按钮
        paint.setColor(mKnobColor);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRoundRect(knobBound, mKnobD, mKnobD, paint);

    }


    /**
     * 设置关到开的 进度
     *
     * @param progress  【0， 1.0】 0 ： 关， 1.0 ： 开
     * @param isRefresh 是否刷新UI
     */
    void setProgress(final float progress, boolean isRefresh) {
        this.progress = progress;
        mKnobCircleCenter.x =
                (mRightCircleCenter.x - mLeftCircleCenter.x) * progress + mLeftCircleCenter.x;

        setupKnobBound();

        mKnobColor = RGBColorTransform(progress, mKnobClosedColor, mKnobOpenedColor);
        if (isRefresh)
            invalidate();
    }

    float getProgress() {
        return progress;
    }

    /**
     * 设置按钮的绘制范围
     */
    private void setupKnobBound() {
        float knobExpandWidth = knobMaxExpand * knobExpandRate; // 按钮拓展增加的宽度
        knobBound.set(mKnobCircleCenter.x - mKnobD / 2 - knobExpandWidth * progress,
                mKnobCircleCenter.y - mKnobD / 2,
                mKnobCircleCenter.x + mKnobD / 2 + knobExpandWidth * (1 - progress),
                mKnobCircleCenter.y + mKnobD / 2
        );
    }

    void setKnobExpandRate(float rate) {
        this.knobExpandRate = rate;
        setupKnobBound();
        invalidate();
    }

    float getKnobExpandRate() {
        return knobExpandRate;
    }

    private int RGBColorTransform(float progress, int fromColor, int toColor) {
        int or = (fromColor >> 16) & 0xFF;
        int og = (fromColor >> 8) & 0xFF;
        int ob = fromColor & 0xFF;

        int nr = (toColor >> 16) & 0xFF;
        int ng = (toColor >> 8) & 0xFF;
        int nb = toColor & 0xFF;

        int rGap = (int) ((float) (nr - or) * progress);
        int gGap = (int) ((float) (ng - og) * progress);
        int bGap = (int) ((float) (nb - ob) * progress);

        return 0xFF000000 | ((or + rGap) << 16) | ((og + gGap) << 8)
                | (ob + bGap);

    }

    public float dp2px(float dip) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return dip * scale;
    }

    public OnSwitchStateChangeListener getOnSwitchStateChangeListener() {
        return onSwitchStateChangeListener;
    }

    public void setOnSwitchStateChangeListener(OnSwitchStateChangeListener
                                                       onSwitchStateChangeListener) {
        this.onSwitchStateChangeListener = onSwitchStateChangeListener;
    }

    /**
     * 状态变化
     */
    public interface OnSwitchStateChangeListener {

        void onSwitchStateChange(SwitchView view, boolean isOn);
    }
}
