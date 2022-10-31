package com.android.exercise.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.android.exercise.R;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.base.toolbar.ToolbarFactory;
import com.android.exercise.ui.widget.refresh.RefreshHeader;
import com.wangzhen.commons.toolbar.impl.Toolbar;
import com.wangzhen.refresh.RefreshLayout;
import com.wangzhen.refresh.callback.OnRefreshCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * RefreshLayoutActivity 自定义下拉刷新布局
 * Created by wangzhen on 2019/3/26.
 */
public class RefreshLayoutActivity extends BaseActivity implements OnRefreshCallback {

    @BindView(R.id.refresh)
    RefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh_layout);
        ButterKnife.bind(this);
        refreshLayout.setOnRefreshCallback(this);
        refreshLayout.setHeaderView(new RefreshHeader(this));
    }

    @Override
    public Toolbar createToolbar() {
        return ToolbarFactory.themed(this, getString(R.string.item_refresh_layout));
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
