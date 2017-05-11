package com.android.exercise.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * 监听开机广播
 * Created by wangzhen on 2017/5/11.
 */

public class StartupReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("TAG", "监听到开机启动");
        PollService.setServiceAlarm(context, true);
    }
}
