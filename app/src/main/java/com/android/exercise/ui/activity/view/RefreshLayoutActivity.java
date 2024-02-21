package com.android.exercise.ui.activity.view;

import android.os.Bundle;
import android.os.Handler;

import com.android.exercise.R;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.base.toolbar.ToolbarFactory;
import com.android.exercise.databinding.ActivityRefreshLayoutBinding;
import com.android.exercise.ui.widget.refresh.RefreshHeader;
import com.wangzhen.commons.toolbar.impl.Toolbar;
import com.wangzhen.refresh.callback.OnRefreshCallback;

/**
 * RefreshLayoutActivity 自定义下拉刷新布局
 * Created by wangzhen on 2019/3/26.
 */
public class RefreshLayoutActivity extends BaseActivity implements OnRefreshCallback {

    private ActivityRefreshLayoutBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((binding = ActivityRefreshLayoutBinding.inflate(getLayoutInflater())).getRoot());
        setEvents();
        binding.refresh.setOnRefreshCallback(this);
        binding.refresh.setHeaderView(new RefreshHeader(this));
    }

    @Override
    public Toolbar createToolbar() {
        return ToolbarFactory.themed(this, getString(R.string.item_refresh_layout));
    }

    public void setEvents() {
        binding.btnStart.setOnClickListener(v -> binding.refresh.startRefresh());
        binding.btnStop.setOnClickListener(v -> binding.refresh.refreshComplete());
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(() -> binding.refresh.refreshComplete(), 1500);
    }
}
