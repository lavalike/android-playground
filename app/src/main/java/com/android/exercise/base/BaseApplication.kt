package com.android.exercise.base;

import android.content.Context;
import android.content.pm.PackageManager;

import androidx.multidex.MultiDexApplication;

import com.android.exercise.base.manager.AppManager;
import com.android.exercise.util.AppUtil;

import org.litepal.LitePal;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Administrator on 2016/4/12.
 */
public class BaseApplication extends MultiDexApplication {
    private static BaseApplication mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        AppUtil.init(this);
        Realm.init(this);
        Realm.setDefaultConfiguration(new RealmConfiguration
                .Builder()
                .name("AndroidExercise.realm")
                .deleteRealmIfMigrationNeeded()
                .build());
        LitePal.initialize(this);
    }

    public static String getAppName() {
        PackageManager pm = AppManager.get().getActivity().getPackageManager();
        return AppManager.get().getActivity().getApplicationInfo().loadLabel(pm)
                .toString();
    }

    public static Context getContext() {
        return mContext;
    }

}
