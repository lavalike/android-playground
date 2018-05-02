package com.android.exercise.util;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;

import com.android.exercise.annotation.InjectLayout;
import com.android.exercise.annotation.InjectView;

import java.lang.reflect.Field;

/**
 * 处理View注解
 * Created by wangzhen on 2018/5/2.
 */
public class ViewInjectUtils {

    /**
     * 绑定
     *
     * @param object 必须是Activity的子类
     */
    public static void bind(Object object) {
        if (object instanceof AppCompatActivity) {
            AppCompatActivity activity = (AppCompatActivity) object;
            get().injectLayout(activity);
            get().injectView(activity);
        }
    }

    private static class SingletonHolder {
        static ViewInjectUtils INSTANCE = new ViewInjectUtils();
    }

    private static ViewInjectUtils get() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 通过运行时注解获取View实例
     *
     * @param activity activity
     */
    private void injectView(Activity activity) {
        Class<?> clazz = activity.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            try {
                if (field.getAnnotations() != null) {
                    if (field.isAnnotationPresent(InjectView.class)) {
                        field.setAccessible(true);
                        InjectView annotation = field.getAnnotation(InjectView.class);
                        field.set(activity, activity.findViewById(annotation.value()));
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 通过运行时注解设置contentView
     *
     * @param activity activity
     */
    private void injectLayout(Activity activity) {
        Class<?> clazz = activity.getClass();
        if (clazz.getAnnotations() != null) {
            if (clazz.isAnnotationPresent(InjectLayout.class)) {
                InjectLayout annotation = clazz.getAnnotation(InjectLayout.class);
                activity.setContentView(annotation.value());
            }
        }
    }
}
