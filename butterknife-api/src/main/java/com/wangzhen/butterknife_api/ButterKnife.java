package com.wangzhen.butterknife_api;

import android.app.Activity;

import java.lang.reflect.Constructor;

/**
 * ButterKnife
 * Created by wangzhen on 2018/5/4.
 */
public class ButterKnife {

    public static void bind(Activity activity) {
        String classFullName = activity.getClass().getName() + "$$ViewBinder";
        try {
            // 2、
            Class proxy = Class.forName(classFullName);
            // 3、
            Constructor constructor = proxy.getConstructor(activity.getClass());
            // 4、
            constructor.newInstance(activity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
