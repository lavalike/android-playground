package com.android.exercise.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.android.exercise.R;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.base.toolbar.ToolbarFactory;
import com.wangzhen.commons.toolbar.impl.Toolbar;

public class BroadcastActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast);
        sendBroadcast(new Intent("com.getui.gis.action.100"));
        sendBroadcast(new Intent("com.getui.gis.action.200"));
    }

    @Nullable
    @Override
    public Toolbar createToolbar() {
        return ToolbarFactory.themed(this, getString(R.string.item_broadcast));
    }
}
