package com.android.exercise.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.android.exercise.domain.NotificationBean;
import com.android.exercise.ui.MainActivity;
import com.android.exercise.util.IKey;

import java.io.Serializable;

/**
 * 推送在通知栏显示 - 点击发送的广播
 * Created by wangzhen on 2018/9/20.
 */
public class NotifyPushReceiver extends BroadcastReceiver {

    public static final String ACTION = "com.android.exercise.push.click";

    @Override
    public void onReceive(Context context, Intent intent) {
        Serializable extra = intent.getSerializableExtra(IKey.PUSH_DATA);
        if (extra instanceof NotificationBean) {
            intent = new Intent(context, MainActivity.class).putExtra(IKey.PUSH_DATA, extra);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // 新栈开启
            context.startActivity(intent);
        }

    }
}
