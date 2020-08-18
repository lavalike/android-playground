package com.android.aspectj;

/**
 * TrackPoint
 * Created by wangzhen on 2020/8/18.
 */
public class TrackPoint {
    private static TrackPointCallback mCallback;

    private TrackPoint() {
    }

    public static void init(TrackPointCallback callback) {
        mCallback = callback;
    }

    static void onClick(String className, String viewId) {
        if (mCallback == null)
            return;
        mCallback.onClick(className, viewId);
    }

    static void onLongClick(String className, String viewId) {
        if (mCallback == null)
            return;
        mCallback.onLongClick(className, viewId);
    }
}
