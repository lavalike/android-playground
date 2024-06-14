package com.android.playground.base.manager;

import android.app.Activity;

import java.util.Stack;

import javax.annotation.Nullable;

/**
 * 管理所有Activity
 * Created by wangzhen on 16/10/20.
 */

public class AppManager {
    private static AppManager mInstance;
    /**
     * 存储所有打开的Activity
     */
    private static Stack<Activity> mActivityStack;

    public static AppManager get() {
        if (mInstance == null) {
            synchronized (AppManager.class) {
                mInstance = new AppManager();
            }
        }
        return mInstance;
    }

    /**
     * 获取栈顶元素
     */
    @Nullable
    public Activity getActivity() {
        if (mActivityStack != null) {
            return mActivityStack.peek();
        }
        return null;
    }

    /**
     * 添加Activity
     */
    public void addActivity(Activity activity) {
        if (mActivityStack == null) mActivityStack = new Stack<>();
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
     */
    public void removeActivity(Activity activity) {
        if (mActivityStack != null) {
            mActivityStack.remove(activity);
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

    public int getCount() {
        if (mActivityStack != null) {
            return mActivityStack.size();
        }
        return 0;
    }
}
