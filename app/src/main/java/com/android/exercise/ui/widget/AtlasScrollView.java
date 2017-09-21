package com.android.exercise.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * 自定义ScrollView
 * Created by wangzhen on 2017/9/21.
 */
public class AtlasScrollView extends ScrollView {
    private static final String TAG = AtlasScrollView.class.getSimpleName();
    private final int mMaxheight;
    private int lastY;
    private int lastInterceptY;
    private int mScreenHeight;

    public AtlasScrollView(Context context) {
        this(context, null);
    }

    public AtlasScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AtlasScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setClickable(true);
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        mScreenHeight = metrics.heightPixels;
        mMaxheight = dip2px(150);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        heightSize = heightSize <= mMaxheight ? heightSize : mMaxheight;
        int maxHeightSpec = MeasureSpec.makeMeasureSpec(heightSize, heightMode);
        super.onMeasure(widthMeasureSpec, maxHeightSpec);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                lastY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                int diff = (int) (ev.getY() - lastY);
                int currHeight = getBottom() - (getTop() + diff);
                if (currHeight > getMeasuredHeight() && currHeight < mScreenHeight / 2) {
                    layout(getLeft(), getTop() + diff, getRight(), getBottom());
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return true;
    }

    private int dip2px(float dipValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
