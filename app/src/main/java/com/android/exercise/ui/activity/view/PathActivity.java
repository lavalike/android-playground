package com.android.exercise.ui.activity.view;

import android.os.Bundle;

import com.android.exercise.base.BaseActivity;
import com.android.exercise.ui.widget.PathView;

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
