package com.android.exercise.base.manager;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;

import com.android.exercise.R;
import com.android.exercise.domain.NotificationBean;
import com.android.exercise.listener.INotification;
import com.android.exercise.receiver.NotifyPushReceiver;
import com.android.exercise.util.IKey;

/**
 * 发送通知助手
 * Created by wangzhen on 2018/9/19.
 */
public class NotificationHelper implements INotification {
    private static INotification mInstance;
    private static int notify_id = 0;
    public static final String CHANNEL_ID = "channel_id_24h";
    public static final String CHANNEL_NAME = "浙江24小时";
    private static final long NO_DISTURBING = 1500; // 免打扰间隔时间
    private static long mLastNotifyTime;
    private static NotificationManager notificationManager;
    private Context context;

    public static INotification get(Context ctx) {
        if (mInstance == null) {
            synchronized (NotificationHelper.class) {
                if (mInstance == null) {
                    mInstance = new NotificationHelper(ctx);
                }
            }
        }
        return mInstance;
    }

    private NotificationHelper(Context ctx) {
        this.context = ctx.getApplicationContext();
    }

    /**
     * 发送通知
     *
     * @param data data
     */
    @Override
    public void send(NotificationBean data) {
        if (data != null) {
            createNotificationChannel();
            Notification notification = createNotification(data);
            if (notification != null) {
                mLastNotifyTime = SystemClock.uptimeMillis();
                getNotificationManager().notify(notify_id++, notification);
            }
        }
    }

    /**
     * 创建一个Notification
     *
     * @param data data
     * @return Notification
     */
    private Notification createNotification(NotificationBean data) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID);
        builder.setContentIntent(getBroadcastIntent(data))
                .setWhen(System.currentTimeMillis())
                .setPriority(Notification.PRIORITY_DEFAULT)
                .setOngoing(false)
                .setSmallIcon(R.mipmap.ic_burger)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_burger))
                .setAutoCancel(true)
                .setContentTitle(data.getTitle())
                .setContentText(data.getSummary());
        if (SystemClock.uptimeMillis() - mLastNotifyTime < NO_DISTURBING) {
            builder.setDefaults(Notification.DEFAULT_LIGHTS);
        } else {
            builder.setDefaults(Notification.DEFAULT_ALL);
        }
        return builder.build();
    }

    /**
     * 创建NotificationChannel
     */
    @Override
    public void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableLights(true);
            channel.setShowBadge(true);
            channel.enableVibration(true);
            channel.setLightColor(Color.YELLOW);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            getNotificationManager().createNotificationChannel(channel);
        }
    }

    /**
     * 获取通知栏点击意图，使用广播
     * 1、当前App在运行，直接打开详情页
     * 2、当前App不在运行，开启MainActivity并携带数据，在其生命周期中开启对应的Activity
     *
     * @param action action
     */
    private PendingIntent getBroadcastIntent(NotificationBean action) {
        Intent intent = new Intent(context, NotifyPushReceiver.class);
        intent.setAction(NotifyPushReceiver.ACTION);
        intent.putExtra(IKey.PUSH_DATA, action);
        // 第二个参数不能重复，否则点击事件会没有响应
        return PendingIntent.getBroadcast(context, notify_id, intent, PendingIntent.FLAG_CANCEL_CURRENT);
    }

    /**
     * 获取 NotificationManager
     *
     * @return NotificationManager
     */
    @Override
    public NotificationManager getNotificationManager() {
        if (notificationManager == null) {
            notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return notificationManager;
    }
}
