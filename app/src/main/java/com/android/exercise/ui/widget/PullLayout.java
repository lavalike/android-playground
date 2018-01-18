package com.android.exercise.ui.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.android.exercise.R;

/**
 * 弹性布局
 * Created by wangzhen on 2018/1/18.
 */
public class PullLayout extends FrameLayout {
    private View contentView;
    private float startY;
    private boolean isMoved;
    private Rect originalRect = new Rect();
    private float factor = 0.3f;
    private int pullDirection = PullDirection.DIRECTION_NONE;
    private View behindView;
    private boolean isCanPullDown;
    //上一次Y距离
    private int lastDeltaY;

    public PullLayout(Context context) {
        super(context);
    }

    public PullLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PullLayout);
        factor = typedArray.getFloat(R.styleable.PullLayout_pl_factor, 0.3f);
        pullDirection = typedArray.getInt(R.styleable.PullLayout_pl_direction, PullDirection.DIRECTION_NONE);
        int resourceId = typedArray.getResourceId(R.styleable.PullLayout_pl_behind_view, -1);
        typedArray.recycle();
        if (resourceId > -1) {
            behindView = LayoutInflater.from(context).inflate(resourceId, null);
        }
        if (behindView != null) {
            addView(behindView, 0);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() > 0) {
            contentView = getChildAt(getChildCount() - 1);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        //存储子控件位置信息
        if (contentView != null)
            originalRect.set(contentView.getLeft(), contentView.getTop(), contentView.getRight(), contentView.getBottom());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (contentView == null) {
            return super.dispatchTouchEvent(ev);
        }
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isCanPullDown = isCanPullDown();
                startY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (!isCanPullDown) break;
                int deltaY = (int) (ev.getY() - startY);
                dragMove(deltaY);
                break;
            case MotionEvent.ACTION_UP:
                if (!isMoved) break;
                collapse();
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 判断拖动类型
     *
     * @param deltaY Y方向移动距离
     */
    private void dragMove(int deltaY) {
        if (pullDirection == PullDirection.DIRECTION_TOP) {
            if (isCanPullDown && deltaY > 0) {
                int offset = (int) (deltaY * factor);
                contentView.layout(
                        originalRect.left,
                        originalRect.top + offset,
                        originalRect.right,
                        originalRect.bottom + offset
                );
                lastDeltaY = deltaY;
                isMoved = true;
            }
        }
    }

    /**
     * 闭合动画
     */
    private void collapse() {
        ValueAnimator animator = ValueAnimator.ofInt(contentView.getTop(), originalRect.top);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                contentView.layout(
                        originalRect.left,
                        value,
                        originalRect.right,
                        originalRect.bottom + value
                );
            }
        });
        animator.start();
        isMoved = false;
    }

    /**
     * 判断是否滚动到顶部
     */
    private boolean isCanPullDown() {
        return contentView.getScrollY() == 0 ||
                contentView.getHeight() < getHeight() + getScrollY();
    }

    /**
     * 获取背景View
     *
     * @return 背景View
     */
    public View getBehindView() {
        return behindView;
    }

    /**
     * 拖动方向
     */
    public static class PullDirection {
        static int DIRECTION_NONE = 0;
        static int DIRECTION_TOP = 1;
    }
}
