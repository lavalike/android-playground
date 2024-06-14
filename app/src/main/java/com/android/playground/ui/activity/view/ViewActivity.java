package com.android.playground.ui.activity.view;

import android.os.Bundle;

import com.android.playground.R;
import com.android.playground.base.BaseActivity;

/**
 * 自定义View
 * Created by wangzhen on 2017/4/22.
 */
public class ViewActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
    }
}
