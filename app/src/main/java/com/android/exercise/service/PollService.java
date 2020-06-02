package com.android.exercise.service;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.Nullable;

/**
 * PollService.java
 * 通过AlarmManager定时启动，退出应用和杀死后台均不影响，重启后AlarmManager失效
 * Created by wangzhen on 2017/5/11.
 */
public class PollService extends IntentService {

    private static String NAME = PollService.class.getName();

    public PollService() {
        super(NAME);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.e("TAG", "PollService已启动");
    }

    public static void setServiceAlarm(Context context, boolean isOn) {
        Intent intent = new Intent(context, PollService.class);
        PendingIntent pi = PendingIntent.getService(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (isOn) {
            alarmManager.setRepeating(AlarmManager.RTC, System.currentTimeMillis(), 1000 * 60, pi);
        } else {
            alarmManager.cancel(pi);
            pi.cancel();
        }
    }
}
