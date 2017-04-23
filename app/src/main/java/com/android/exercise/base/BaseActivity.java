package com.android.exercise.base;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.exercise.base.manager.AppManager;
import com.android.exercise.util.PermissionManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

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
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
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

    /**
     * 设置状态栏模式: 正常/亮色
     *
     * @param textDark 是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    public final boolean setStatusBarMode(boolean textDark) {

        if (setFlymeStatusBarLightMode(getWindow(), textDark)
                | setMIUIStatusBarLightMode(getWindow(), textDark)) {
            return true;
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            int visibility = textDark
                    ? (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
                    : View.SYSTEM_UI_FLAG_VISIBLE;
            getWindow().getDecorView().setSystemUiVisibility(visibility);
            return true;
        }

        return false;
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


    /**
     * 设置状态栏图标为深色和魅族特定的文字风格，Flyme4.0以上
     * 可以用来判断是否为Flyme用户
     *
     * @param window 需要设置的窗口
     * @param dark   是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    private static boolean setFlymeStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            try {
                WindowManager.LayoutParams lp = window.getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class
                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class
                        .getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null); // 获取的静态字段
                int value = meizuFlags.getInt(lp);
                if (dark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                window.setAttributes(lp);
                result = true;
            } catch (Exception e) {

            }
        }
        return result;
    }

    /**
     * 设置状态栏字体图标为深色，需要MIUIV6以上
     *
     * @param window 需要设置的窗口
     * @param dark   是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    private static boolean setMIUIStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            Class clazz = window.getClass();
            try {
                int darkModeFlag = 0;
                Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                if (dark) {
                    extraFlagField.invoke(window, darkModeFlag, darkModeFlag);//状态栏透明且黑色字体
                } else {
                    extraFlagField.invoke(window, 0, darkModeFlag);//清除黑色字体
                }
                result = true;
            } catch (Exception e) {

            }
        }
        return result;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.get().removeActivity(this);
    }
}
