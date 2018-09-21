package com.android.exercise.listener;

import android.app.NotificationManager;

import com.android.exercise.domain.NotificationBean;

/**
 * 通知栏接口
 * Created by wangzhen on 2018/9/20.
 */
public interface INotification {
    /**
     * 创建createNotificationChannel
     */
    void createNotificationChannel();

    /**
     * 获取NotificationManager
     *
     * @return NotificationManager
     */
    NotificationManager getNotificationManager();

    /**
     * 发送普通通知
     *
     * @param data data
     */
    void send(NotificationBean data);
}
