package com.android.exercise.base;

import android.content.Context;
import android.content.pm.PackageManager;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.android.aspectj.TrackPoint;
import com.android.aspectj.TrackPointCallback;
import com.android.exercise.base.manager.AppManager;
import com.android.exercise.base.okhttp.OKHttpManager;
import com.android.exercise.util.AppUtil;
import com.android.exercise.util.L;
import com.zhy.http.okhttp.OkHttpUtils;

import org.xutils.DbManager;
import org.xutils.x;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * 自定义Application
 * Created by Administrator on 2016/4/12.
 */
public class BaseApplication extends MultiDexApplication {

    //SQLite配置
    private static DbManager.DaoConfig db_config;
    private static BaseApplication mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        AppUtil.init(this);
        Realm.init(this);
        //初始化Realm
        RealmConfiguration config = new RealmConfiguration
                .Builder()
                .name("AndroidExercise.realm")
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
        //初始化xUtils3
        x.Ext.init(this);
        OkHttpUtils.initClient(OKHttpManager.getClient());

        TrackPoint.init(new TrackPointCallback() {
            @Override
            public void onClick(String className, String viewId) {
                L.e("onClick -> className : " + className + " viewId : " + viewId);
            }

            @Override
            public void onLongClick(String className, String viewId) {
                L.e("onLongClick -> className : " + className + " viewId : " + viewId);
            }
        });
    }

    /**
     * 获取Db的Config
     *
     * @return
     */
    public static DbManager.DaoConfig getDbConfig() {
        if (db_config == null) {
            synchronized (BaseApplication.class) {
                db_config = new DbManager.DaoConfig().setDbName("ExerciseDemo.db");
            }
        }
        return db_config;
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

    public static Context getContext() {
        return mContext;
    }

}
