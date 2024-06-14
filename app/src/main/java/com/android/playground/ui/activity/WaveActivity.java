package com.android.playground.ui.activity;

import android.os.Bundle;

import com.android.playground.R;
import com.android.playground.base.BaseActivity;
import com.android.playground.base.toolbar.ToolbarFactory;
import com.wangzhen.commons.toolbar.impl.Toolbar;

/**
 * 波浪效果
 * Created by wangzhen on 2017/8/16.
 */
public class WaveActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wave);
    }

    @Override
    public Toolbar createToolbar() {
        return ToolbarFactory.themed(this, getString(R.string.item_wave_view));
    }
}
