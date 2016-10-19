package com.android.exercise.common.toolbar;

import android.app.Activity;
import android.view.View;

import com.android.exercise.R;

/**
 * 自定义Toolbar基类
 * Created by wangzhen on 16/10/19.
 */

public abstract class BaseToolBarHolder implements View.OnClickListener {

    protected android.support.v7.widget.Toolbar mToolbar;

    protected Activity mActivity;

    public View mBackView;

    private OnClickListener mOnClickListener;

    public BaseToolBarHolder(final Activity activity, android.support.v7.widget.Toolbar mToolbar) {
        this.mActivity = activity;
        this.mToolbar = mToolbar;
        mToolbar.showOverflowMenu();
        mToolbar.setContentInsetsRelative(0, 0);
        //加载自定义布局
        View.inflate(activity, getToolBarLayoutResId(), mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mActivity != null) {
                    mActivity.finish();
                }
            }
        });
        initView();
    }

    /**
     * 设置ToolBar所有可点击控件的点击监听
     *
     * @param mOnClickListener
     */
    public void setAllOnClickListener(OnClickListener mOnClickListener) {
        this.mOnClickListener = mOnClickListener;
    }

    /**
     * see Activity.findViewById()
     */
    public <T extends View> T findById(int id) {
        return (T) mToolbar.findViewById(id);
    }

    protected abstract void initView();

    /**
     * 获取自定义ToolBar视图的资源id
     *
     * @return
     */
    protected abstract int getToolBarLayoutResId();


    @Override
    public void onClick(View v) {
        if (null != mOnClickListener) {
            mOnClickListener.onClick(v);
        }
    }

    /**
     * 点击回调接口
     */
    public interface OnClickListener {
        void onClick(View v);
    }

}