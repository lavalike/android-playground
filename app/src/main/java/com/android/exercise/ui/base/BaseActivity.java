package com.android.exercise.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.android.exercise.util.App;

/**
 * Activity基类
 * Created by Administrator on 2016/4/12.
 */
public class BaseActivity extends ToolbarActivity {

    public Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        App.get().addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.get().removeActivity(this);
    }
}
