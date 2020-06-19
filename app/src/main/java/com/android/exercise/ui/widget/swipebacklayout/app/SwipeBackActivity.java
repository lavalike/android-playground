
package com.android.exercise.ui.widget.swipebacklayout.app;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.android.exercise.ui.widget.swipebacklayout.SwipeBackLayout;
import com.android.exercise.ui.widget.swipebacklayout.Utils;

public class SwipeBackActivity extends AppCompatActivity implements SwipeBackActivityBase {
    private SwipeBackActivityHelper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHelper = new SwipeBackActivityHelper(this);
        mHelper.onActivityCreate();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mHelper.onPostCreate();
    }

    @Override
    public <T extends View> T findViewById(int id) {
        T v = super.findViewById(id);
        if (v == null && mHelper != null)
            return mHelper.findViewById(id);
        return v;
    }

    @Override
    public SwipeBackLayout getSwipeBackLayout() {
        return mHelper.getSwipeBackLayout();
    }

    @Override
    public void setSwipeBackEnable(boolean enable) {
        getSwipeBackLayout().setEnableGesture(enable);
    }

    @Override
    public void scrollToFinishActivity() {
        Utils.convertActivityToTranslucent(this);
        getSwipeBackLayout().scrollToFinishActivity();
    }

    /**
     * 设置边缘滑动退出方式
     *
     * @param edgeFlags SwipeBackLayout.EDGE_LEFT 左边缘滑动退出
     *                  SwipeBackLayout.EDGE_RIGHT 右边缘滑动退出
     *                  SwipeBackLayout.EDGE_BOTTOM 下边缘滑动退出
     *                  SwipeBackLayout.EDGE_ALL 左、右、下边缘滑动退出
     */
    public void setEdgeTrackingEnabled(int edgeFlags) {
        getSwipeBackLayout().setEdgeTrackingEnabled(edgeFlags);
    }
}
