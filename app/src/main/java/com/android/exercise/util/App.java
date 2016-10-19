package com.android.exercise.util;

import android.app.Activity;
import android.app.Application;

import java.util.Stack;

/**
 * Created by Administrator on 2016/4/12.
 */
public class App extends Application {

    private static App mInstance;
    /**
     * 存储所有打开的Activity
     */
    private Stack<Activity> mActivityStack;

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
        if (mActivityStack == null)
            mActivityStack = new Stack<>();
        mActivityStack.push(activity);
    }

    /**
     * 移除顶部Activity
     */
    public void removeTop() {
        if (mActivityStack != null) {
            mActivityStack.pop();
        }
    }

    /**
     * 删除Activity
     *
     * @param activity
     */
    public void removeActivity(Activity activity) {
        if (mActivityStack != null) {
            if (mActivityStack.contains(activity)) {
                mActivityStack.remove(activity);
            }
        }
    }

    /**
     * 清空所有Activity
     */
    public void removeAllActivity() {
        if (mActivityStack != null) {
            mActivityStack.clear();
        }
    }

}
