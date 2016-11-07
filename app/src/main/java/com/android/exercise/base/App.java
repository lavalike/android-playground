package com.android.exercise.base;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.android.exercise.base.manager.AppManager;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * 自定义Application
 * Created by Administrator on 2016/4/12.
 */
public class App extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration
                .Builder()
                .name("AndroidExercise.realm")
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
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
