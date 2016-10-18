package com.android.exercise.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/4/12.
 */
public class T {
    private static T mInstance;
    private static Toast mToast;

    public static T get(Context context) {
        if (mInstance == null || mToast == null) {
            synchronized (T.class) {
                mInstance = new T();
                mToast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
            }
        }
        return mInstance;
    }

    /***
     * 显示
     *
     * @param msg
     */
    public void toast(String msg) {
        if (mToast != null) {
            mToast.setText(msg);
            mToast.show();
        }
    }
}