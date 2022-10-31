package com.android.exercise.ui.activity;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.RemoteViews;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.android.exercise.R;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.base.manager.NotificationHelper;
import com.android.exercise.base.toolbar.ToolbarFactory;
import com.android.exercise.domain.NotificationBean;
import com.android.exercise.listener.INotification;
import com.android.exercise.ui.MainActivity;
import com.wangzhen.commons.toolbar.impl.Toolbar;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class NotificationActivity extends BaseActivity {

    private INotification notification;
    private int notifyId = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        ButterKnife.bind(this);
        notification = NotificationHelper.get(this);
    }

    @Nullable
    @Override
    public Toolbar createToolbar() {
        return ToolbarFactory.themed(this, getString(R.string.item_notification));
    }

    @OnClick({R.id.btn_send_normal, R.id.btn_send_custom, R.id.btn_send_big_text, R.id.btn_send_inbox, R.id.btn_send_big_picture, R.id.btn_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_send_normal:
                sendNormal();
                break;
            case R.id.btn_send_custom:
                sendCustomLayout();
                break;
            case R.id.btn_send_big_text:
                sendBigTextStyle();
                break;
            case R.id.btn_send_inbox:
                sendInboxStyle();
                break;
            case R.id.btn_send_big_picture:
                sendBigPictureStyle();
                break;
            case R.id.btn_delete:
//                notification.getNotificationManager().cancelAll();
                notification.getNotificationManager().cancel(--notifyId);
                break;
        }
    }

    private void sendBigPictureStyle() {
        notification.createNotificationChannel();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NotificationHelper.CHANNEL_ID);
        builder.setWhen(System.currentTimeMillis()).setPriority(Notification.PRIORITY_DEFAULT).setOngoing(false).setSmallIcon(R.mipmap.ic_burger).setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_burger)).setAutoCancel(true).setContentTitle("看完这篇文章，你的Linux基础就差不多了").setContentText("内核(kernel)是系统的心脏，是运行程序和管理像磁盘和打印机等硬件设备的核心程序，它提供了一个在裸设备与应用程序间的抽象层");
        NotificationCompat.BigPictureStyle style = new NotificationCompat.BigPictureStyle();
        style.bigPicture(BitmapFactory.decodeResource(getResources(), R.mipmap.bg_5));
        builder.setStyle(style);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setContentIntent(pendingIntent);
        notification.getNotificationManager().notify(notifyId++, builder.build());
    }

    private void sendInboxStyle() {
        notification.createNotificationChannel();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NotificationHelper.CHANNEL_ID);
        builder.setWhen(System.currentTimeMillis()).setPriority(Notification.PRIORITY_DEFAULT).setOngoing(false).setSmallIcon(R.mipmap.ic_burger).setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_burger)).setAutoCancel(true);
        NotificationCompat.InboxStyle style = new NotificationCompat.InboxStyle();
        style.setBigContentTitle("看完这篇文章，你的Linux基础就差不多了").addLine("第二行").addLine("第三行").addLine("第四行").addLine("第五行").addLine("第六行").addLine("第七行").addLine("第八行");
        builder.setStyle(style);
        builder.setContentText("内核(kernel)是系统的心脏，是运行程序和管理像磁盘和打印机等硬件设备的核心程序，它提供了一个在裸设备与应用程序间的抽象层");
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setContentIntent(pendingIntent);
        notification.getNotificationManager().notify(notifyId++, builder.build());
    }

    private void sendBigTextStyle() {
        notification.createNotificationChannel();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NotificationHelper.CHANNEL_ID);
        builder.setWhen(System.currentTimeMillis()).setPriority(Notification.PRIORITY_DEFAULT).setOngoing(false).setSmallIcon(R.mipmap.ic_burger).setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_burger)).setAutoCancel(true).setContentTitle("看完这篇文章，你的Linux基础就差不多了").setContentText("内核(kernel)是系统的心脏，是运行程序和管理像磁盘和打印机等硬件设备的核心程序，它提供了一个在裸设备与应用程序间的抽象层");
        builder.setStyle(new NotificationCompat.BigTextStyle());
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setContentIntent(pendingIntent);
        notification.getNotificationManager().notify(notifyId++, builder.build());
    }

    private void sendCustomLayout() {
        notification.createNotificationChannel();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NotificationHelper.CHANNEL_ID);
        builder.setWhen(System.currentTimeMillis()).setPriority(Notification.PRIORITY_DEFAULT).setOngoing(false).setSmallIcon(R.mipmap.ic_burger).setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_burger)).setAutoCancel(true).setContentTitle("自定义布局title").setContentText("自定义布局text");
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.layout_notification_custom);
        builder.setCustomBigContentView(remoteViews);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setContentIntent(pendingIntent);
        notification.getNotificationManager().notify(notifyId++, builder.build());
    }

    private void sendNormal() {
        notification.send(new NotificationBean.Builder().setTitle("看完这篇文章，你的Linux基础就差不多了").setSummary("内核(kernel)是系统的心脏，是运行程序和管理像磁盘和打印机等硬件设备的核心程序，它提供了一个在裸设备与应用程序间的抽象层").build());
    }
}
