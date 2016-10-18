package com.android.exercise.util;

import android.app.Activity;
import android.app.Application;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/12.
 */
public class App extends Application {

    private static App mInstance;
    /**
     * 存储所有打开的Activity
     */
    private List<Activity> mActivityList;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static App get() {
        if (mInstance == null) {
            synchronized (App.class) {
                mInstance = new App();
            }
        }
        return mInstance;
    }

    /**
     * 添加Activity
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        if (mActivityList == null)
            mActivityList = new ArrayList<>();
//        if (mActivityList.contains(activity))
//            mActivityList.remove(activity);
        mActivityList.add(activity);
    }

    /**
     * 删除Activity
     *
     * @param activity
     */
    public void removeActivity(Activity activity) {
        if (mActivityList != null) {
            if (mActivityList.contains(activity)) {
                mActivityList.remove(activity);
            }
        }
    }

    /**
     * 清空所有Activity
     */
    public void removeAllActivity() {
        if (mActivityList != null) {
            mActivityList.clear();
        }
    }

}
