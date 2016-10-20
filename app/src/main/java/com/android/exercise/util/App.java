package com.android.exercise.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.android.exercise.util.manager.AppManager;

/**
 * 自定义Application
 * Created by Administrator on 2016/4/12.
 */
public class App extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    /**
     * 获取App名称
     */
    public static String getAppName() {
        PackageManager pm = AppManager.get().getActivity().getPackageManager();
        String appName = AppManager.get().getActivity().getApplicationInfo().loadLabel(pm)
                .toString();
        return appName;
    }


}
