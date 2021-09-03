package com.android.exercise.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * GInsightReceiver
 * Created by wangzhen on 2018/1/25.
 */
public class GInsightReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getStringExtra("action");
        if ("".equalsIgnoreCase(action)) {
            String giuid = intent.getStringExtra("giuid");
        }
    }
}
