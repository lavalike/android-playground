package com.android.exercise.ui.activity;

import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import android.view.View;

import com.android.exercise.R;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.base.toolbar.ToolBarCommonHolder;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 事件分发
 */
public class DispatchActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispatch);
        ButterKnife.bind(this);
    }

    @Override
    protected void onSetupToolbar(Toolbar toolbar, ActionBar actionBar) {
        new ToolBarCommonHolder(this, toolbar, getString(R.string.item_dispatch), true);
    }

    @OnClick({R.id.btn_dispatch, R.id.relativelayout_dispatch})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_dispatch:
                showToast("按钮被点击");
                break;
            case R.id.relativelayout_dispatch:
                showToast("relativelayout被点击");
                break;
        }
    }
}
