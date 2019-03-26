package com.android.exercise.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.android.exercise.R;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.base.toolbar.ToolBarCommonHolder;
import com.wangzhen.refresh.RefreshLayout;
import com.wangzhen.refresh.callback.RefreshCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * RefreshLayoutActivity 自定义下拉刷新布局
 * Created by wangzhen on 2019/3/26.
 */
public class RefreshLayoutActivity extends BaseActivity implements RefreshCallback {

    @BindView(R.id.refresh)
    RefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh_layout);
        ButterKnife.bind(this);
        refreshLayout.setRefreshCallback(this);
    }

    @Override
    protected void onSetupToolbar(Toolbar toolbar, ActionBar actionBar) {
        new ToolBarCommonHolder(this, toolbar, getString(R.string.item_refresh_layout));
    }

    @OnClick({R.id.btn_start, R.id.btn_stop})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_start:
                refreshLayout.startRefresh();
                break;
            case R.id.btn_stop:
                refreshLayout.refreshComplete();
                break;
        }
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshLayout.refreshComplete();
            }
        }, 1500);
    }
}
