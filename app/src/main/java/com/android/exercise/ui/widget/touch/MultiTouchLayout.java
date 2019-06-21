package com.android.exercise.ui.widget.touch;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

/**
 * MultiTouchView
 * Created by wangzhen on 2019-05-08.
 */
public class MultiTouchLayout extends LinearLayout {
    private FrameLayout mHeaderView;
    private View mContentView;
    private float mDragFactor = 0.5f;
    private boolean isDragging = false;
    private float lastY;
    private int activePointerId;
    private int pointerIndex;

    public MultiTouchLayout(Context context) {
        this(context, null);
    }

    public MultiTouchLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MultiTouchLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOrientation(LinearLayout.VERTICAL);
        createDefaultHeader();
    }

    private void createDefaultHeader() {
        mHeaderView = new FrameLayout(getContext());
        mHeaderView.setBackgroundColor(Color.parseColor("#a0a4a9"));
        addView(mHeaderView, 0, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0));
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() > 0) {
            mContentView = getChildAt(getChildCount() - 1);
            if (mContentView != null) {
                mContentView.setOverScrollMode(View.OVER_SCROLL_NEVER);
            }
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        pointerIndex = ev.getActionIndex();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                activePointerId = ev.getPointerId(pointerIndex);
                lastY = ev.getY(pointerIndex);
                break;
            case MotionEvent.ACTION_MOVE:
                //存在情况：mContentView内容未在顶部，下拉到顶部时mContentView会直接跳过前面下拉距离
                //解决方法：下拉过程中不断更新lastX、lastY坐标为当前坐标，达到从顶部下拉的效果
                if (isCanPullDown()) {
                    lastY = ev.getY(pointerIndex);
                }
                float diffY = ev.getY(pointerIndex) - lastY;
                if (diffY > 0) {
                    isDragging = true;
                    return true;
                }
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    /**
     * 详见{@link android.support.v4.widget.SwipeRefreshLayout#requestDisallowInterceptTouchEvent(boolean)}
     *
     * @param disallowIntercept disallowIntercept
     */
    @Override
    public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        // if this is a List < L or another view that doesn't support nested
        // scrolling, ignore this request so that the vertical scroll event
        // isn't stolen
        if ((android.os.Build.VERSION.SDK_INT < 21 && mContentView instanceof AbsListView)
                || ViewCompat.isNestedScrollingEnabled(mContentView)
                || isCanPullDown()) {
            // Nope.
        } else {
            super.requestDisallowInterceptTouchEvent(disallowIntercept);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        pointerIndex = event.getActionIndex();
        switch (event.getAction()) {
            case MotionEvent.ACTION_POINTER_DOWN:
                pointerIndex = event.getActionIndex();
                if (pointerIndex < 0)
                    return false;
                activePointerId = event.getPointerId(pointerIndex);
                break;
            case MotionEvent.ACTION_POINTER_UP:
                if (activePointerId == event.getPointerId(pointerIndex)) {
                    int newPointerIndex = pointerIndex == 0 ? 1 : 0;
                    activePointerId = event.getPointerId(newPointerIndex);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                pointerIndex = event.findPointerIndex(activePointerId);
                if (pointerIndex < 0)
                    return false;
                if (isDragging) {
                    float deltaY = event.getY(pointerIndex) - lastY;
                    if (deltaY <= 0) return false;
                    changeHeader(deltaY);
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                pointerIndex = event.findPointerIndex(activePointerId);
                if (pointerIndex < 0)
                    return false;
                if (isDragging) {
                    isDragging = false;
                    restoreHeader();
                }
                activePointerId = MotionEvent.INVALID_POINTER_ID;
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 将HeaderView高度恢复为0
     */
    private void restoreHeader() {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(mHeaderView.getBottom(), 0);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                ViewGroup.LayoutParams layoutParams = mHeaderView.getLayoutParams();
                layoutParams.height = (int) animation.getAnimatedValue();
                mHeaderView.requestLayout();
            }
        });
        valueAnimator.start();
    }

    /**
     * 改变HeaderView的高度
     *
     * @param height 高度
     */
    private void changeHeader(float height) {
        if (height < 0)
            height = 0;
        ViewGroup.LayoutParams layoutParams = mHeaderView.getLayoutParams();
        layoutParams.height = (int) (height * mDragFactor);
        mHeaderView.requestLayout();
    }


    /**
     * 判断是否滚动到顶部
     */
    private boolean isCanPullDown() {
        return mContentView != null && ViewCompat.canScrollVertically(mContentView, -1);
    }
}
