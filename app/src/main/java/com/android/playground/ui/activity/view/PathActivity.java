package com.android.playground.ui.activity.view;

import android.os.Bundle;

import com.android.playground.base.BaseActivity;
import com.android.playground.ui.widget.PathView;

/**
 * PathActivity
 * Created by wangzhen on 2018/11/21.
 */
public class PathActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fitDarkStatus(true);
        setContentView(new PathView(this));
    }
}
