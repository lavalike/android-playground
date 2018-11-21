package com.android.exercise.ui.activity;

import android.os.Bundle;

import com.android.exercise.base.BaseActivity;
import com.android.exercise.ui.widget.PathMeasureView;

/**
 * PathMeasureActivity
 * Created by wangzhen on 2018/11/21.
 */
public class PathMeasureActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fitDarkStatus(true);
        setContentView(new PathMeasureView(this));
    }

    @Override
    public boolean showToolbar() {
        return false;
    }
}
