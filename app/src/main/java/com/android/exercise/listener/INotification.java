package com.android.exercise.listener;

import com.android.exercise.domain.NotificationBean;

/**
 * 通知栏接口
 */
public interface INotification {
    /**
     * 发送通知
     *
     * @param data data
     */
    void send(NotificationBean data);
}
