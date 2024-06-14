package com.android.playground.ui.activity.view;

import android.os.Bundle;

import com.android.playground.base.BaseActivity;
import com.android.playground.ui.widget.PathMeasureView;

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
}
