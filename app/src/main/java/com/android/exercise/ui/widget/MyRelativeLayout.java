package com.android.exercise.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import com.android.exercise.util.T;

/**
 * 测试事件分发
 * Created by wangzhen on 2017/2/24.
 */

public class MyRelativeLayout extends RelativeLayout {

    private int mLastX;
    private int mLastY;

    public MyRelativeLayout(Context context) {
        super(context);
    }

    public MyRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //拦截掉触摸事件，当前View消耗掉，不会再往子View传递
        return false;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int rawX = (int) event.getX();
        int rawY = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastX = rawX;
                mLastY = rawY;
                break;
            case MotionEvent.ACTION_MOVE:
                int offsetX = rawX - mLastX;
                int offsetY = rawY - mLastY;
                layout(
                        getLeft() + offsetX,
                        getTop() + offsetY,
                        getRight() + offsetX,
                        getBottom() + offsetY
                );
                break;
            case MotionEvent.ACTION_UP:
                T.get(getContext()).toast("滑动结束");
                break;
        }
        return true;
    }
}
