package com.android.exercise.util;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/4/12.
 */
public class T {
    private static T mInstance;
    private Toast mToast;

    private T(Context ctx) {
        mToast = Toast.makeText(ctx.getApplicationContext(), "", Toast.LENGTH_SHORT);
        mToast.setGravity(Gravity.CENTER, 0, 0);
    }

    public static T get(Context context) {
        if (mInstance == null) {
            synchronized (T.class) {
                mInstance = new T(context);
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