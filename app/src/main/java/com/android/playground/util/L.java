package com.android.playground.util;

import android.util.Log;

public class L {
    private static final String TAG = "Android";

    public static void e(String msg) {
        e(TAG, msg);
    }

    public static void e(String tag, String msg) {
        Log.e(tag, msg);
    }
}
