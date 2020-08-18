package com.android.aspectj;

/**
 * TrackPointCallback
 * Created by wangzhen on 2020/8/18.
 */
public interface TrackPointCallback {
    void onClick(String className, String viewId);

    void onLongClick(String className, String viewId);
}
