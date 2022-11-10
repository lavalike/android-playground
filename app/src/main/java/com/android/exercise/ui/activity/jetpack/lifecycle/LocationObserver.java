package com.android.exercise.ui.activity.jetpack.lifecycle;

import android.widget.Toast;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import com.android.exercise.util.AppUtil;

/**
 * LocationObserver
 * Created by wangzhen on 2020/6/4.
 */
public class LocationObserver implements LifecycleObserver {
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void connect() {
        Toast.makeText(AppUtil.getContext(), "start locating", Toast.LENGTH_SHORT).show();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void disConnect() {
        Toast.makeText(AppUtil.getContext(), "stop locating", Toast.LENGTH_SHORT).show();
    }
}
