package com.android.exercise.base;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.exercise.base.manager.AppManager;
import com.wangzhen.commons.toolbar.ToolbarActivity;
import com.wangzhen.statusbar.DarkStatusBar;

/**
 * Activity基类
 * Created by Administrator on 2016/4/12.
 */
public abstract class BaseActivity extends ToolbarActivity {

    public Context mContext;
    protected String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        AppManager.get().addActivity(this);
    }

    protected void showToast(String text) {
        Toast.makeText(mContext, text, Toast.LENGTH_SHORT).show();
    }

    public void fitDarkStatus(boolean isDark) {
        if (isDark) DarkStatusBar.get().fitDark(this);
        else DarkStatusBar.get().fitLight(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.get().removeActivity(this);
    }
}
