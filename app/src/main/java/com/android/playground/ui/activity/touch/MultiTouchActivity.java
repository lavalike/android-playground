package com.android.playground.ui.activity.touch;

import android.os.Bundle;

import com.android.playground.R;
import com.android.playground.base.BaseActivity;
import com.android.playground.base.toolbar.ToolbarFactory;
import com.wangzhen.commons.toolbar.impl.Toolbar;

/**
 * MultiTouchActivity 多点触摸
 * Created by wangzhen on 2019-05-08.
 */
public class MultiTouchActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_touch);
    }

    @Override
    public Toolbar createToolbar() {
        return ToolbarFactory.themed(this, getString(R.string.item_multi_touch));
    }
}
