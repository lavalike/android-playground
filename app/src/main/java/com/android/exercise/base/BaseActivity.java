package com.android.exercise.base;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.android.exercise.base.manager.AppManager;
import com.android.exercise.util.PermissionManager;
import com.wangzhen.statusbar.DarkStatusBar;

/**
 * Activity基类
 * Created by Administrator on 2016/4/12.
 */
public class BaseActivity extends ToolbarActivity implements PermissionManager.OnBaseCallback {

    public Context mContext;
    private PermissionManager.OnPermissionCallback mCallback;
    protected String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setSwipeBackEnable(true);
        // 禁止横屏
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        AppManager.get().addActivity(this);
    }

    protected void showToast(String text) {
        Toast.makeText(mContext, text, Toast.LENGTH_SHORT).show();
    }

    protected void logE(String msg) {
        Log.e(TAG, msg);
    }

    protected void logD(String msg) {
        Log.d(TAG, msg);
    }

    public void fitDarkStatus(boolean isDark) {
        if (isDark)
            DarkStatusBar.get().fitDark(this);
        else
            DarkStatusBar.get().fitLight(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //权限被授予
                if (mCallback != null) {
                    mCallback.onGranted();
                }
            } else {
                //权限被拒绝
                if (mCallback != null) {
                    mCallback.onDenied();
                }
            }

        }
    }

    @Override
    public void setPermissionCallback(PermissionManager.OnPermissionCallback callback) {
        mCallback = callback;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.get().removeActivity(this);
    }
}
