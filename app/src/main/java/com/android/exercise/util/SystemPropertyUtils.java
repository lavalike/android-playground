package com.android.exercise.util;

import android.text.TextUtils;
import android.util.Log;

import java.lang.reflect.Method;

/**
 * SystemPropertyUtils

 * Created by wangzhen on 2022/4/7/007
 */
public final class SystemPropertyUtils {
    public static final String TAG = SystemPropertyUtils.class.getSimpleName();
    private static final String ANDROID_SYS_PROP = "android.os.SystemProperties";

    /**
     * get property value by reflect
     *
     * @param name property name
     * @return property value
     */
    public static String get(String name) {
        return get(name, "");
    }

    /**
     * get value by reflect
     *
     * @param name property name
     * @param def  default value
     * @return property value
     */
    public static String get(String name, String def) {
        String property = "";
        Class<?> clazz;
        try {
            clazz = Class.forName(ANDROID_SYS_PROP);
            Method method = clazz.getMethod("get", String.class, String.class);
            property = (String) method.invoke(clazz.newInstance(), name, def);
        } catch (Exception e) {
            Log.e(TAG, "SystemPropertyUtils.get exception: " + e.getMessage());
        } finally {
            if (TextUtils.isEmpty(property)) {
                try {
                    clazz = Class.forName(ANDROID_SYS_PROP);
                    Method method = clazz.getMethod("get", String.class, String.class);
                    property = (String) method.invoke(clazz, name, def);
                } catch (Exception e) {
                    Log.e(TAG, "SystemPropertyUtils.get exception: " + e.getMessage());
                }
            }
        }
        return property;
    }
}
