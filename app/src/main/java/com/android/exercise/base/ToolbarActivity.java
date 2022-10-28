package com.android.exercise.base;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.LayoutRes;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import com.android.exercise.R;
import com.android.exercise.ui.widget.swipebacklayout.app.SwipeBackActivity;

/**
 * 处理Toolbar相关
 * Created by Administrator on 2016/10/18.
 */

public class ToolbarActivity extends SwipeBackActivity {

    private Toolbar mToolbar;
    private LayoutInflater mInflater;
    private FrameLayout mRootView;

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        setContentView(getLayoutInflater().inflate(layoutResID, null), null);
    }

    @Override
    public void setContentView(View view) {
        setContentView(view, null);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        if (showToolbar()) {
            initContentView(view);
            initToolbar();
        } else {
            super.setContentView(view);
        }
    }

    private void initToolbar() {
        super.setContentView(mRootView);
        mInflater = LayoutInflater.from(this);
        View toolbarView = mInflater.inflate(R.layout.toolbar_layout, mRootView);
        mToolbar = (Toolbar) toolbarView.findViewById(R.id.id_tool_bar);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(view -> finish());
        onSetupToolbar(mToolbar, getSupportActionBar());
    }

    /**
     * 设置Toolbar的数据，也可以设置自定义toolBar视图 如需则交给子类去实现
     *
     * @param toolbar
     * @param actionBar 为了兼容性，设置title、subTitle等使用actionBar
     */
    protected void onSetupToolbar(Toolbar toolbar, ActionBar actionBar) {
        // 注意：此段代码在子类的onCreate()方法的setContentView(layoutResId)中执行
        // 特别注意其他View与toolBar的交互
    }

    /**
     * 创建帧布局，作为视图容器的父容器
     *
     * @param view
     */
    private void initContentView(View view) {
        mRootView = new FrameLayout(this);
        //windows设置为PrimaryDark
        mRootView.setBackgroundResource(R.color.colorPrimaryDark);
        mRootView.setFitsSystemWindows(true);
        FrameLayout.LayoutParams paramsRoot = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT);
        mRootView.setLayoutParams(paramsRoot);
        //设置视图顶部的Toolbar高度
        int toolbarSize = (int) getResources().getDimension(R.dimen.toolbar_height);
        FrameLayout.LayoutParams paramsView = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT);
        paramsView.topMargin = toolbarSize;
        Drawable background = view.getBackground();
        if (background == null) {
            //页面布局背景色设置为默认 #F9F9F9
            view.setBackgroundColor(Color.parseColor("#F9F9F9"));
        }
        //将视图添加到父容器
        mRootView.addView(view, paramsView);
    }

    public boolean showToolbar() {
        return true;
    }
}
