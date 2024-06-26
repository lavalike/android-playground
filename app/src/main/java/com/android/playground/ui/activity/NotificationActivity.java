package com.android.playground.ui.activity;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.RemoteViews;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.android.playground.R;
import com.android.playground.base.BaseActivity;
import com.android.playground.base.manager.NotificationHelper;
import com.android.playground.base.toolbar.ToolbarFactory;
import com.android.playground.databinding.ActivityNotificationBinding;
import com.android.playground.domain.NotificationBean;
import com.android.playground.listener.INotification;
import com.android.playground.ui.MainActivity;
import com.wangzhen.commons.toolbar.impl.Toolbar;

public class NotificationActivity extends BaseActivity {
    private ActivityNotificationBinding binding;
    private INotification notification;
    private int notifyId = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((binding = ActivityNotificationBinding.inflate(getLayoutInflater())).getRoot());
        setEvents();
        notification = NotificationHelper.getInstance(this);
    }

    @Nullable
    @Override
    public Toolbar createToolbar() {
        return ToolbarFactory.themed(this, getString(R.string.item_notification));
    }

    public void setEvents() {
        binding.btnSendNormal.setOnClickListener(v -> sendNormal());
        binding.btnSendCustom.setOnClickListener(v -> sendCustomLayout());
        binding.btnSendBigText.setOnClickListener(v -> sendBigTextStyle());
        binding.btnSendInbox.setOnClickListener(v -> sendInboxStyle());
        binding.btnSendBigPicture.setOnClickListener(v -> sendBigPictureStyle());
        binding.btnDelete.setOnClickListener(v -> notification.getNotificationManager().cancel(--notifyId));
    }

    private void sendBigPictureStyle() {
        notification.createNotificationChannel();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NotificationHelper.CHANNEL_ID);
        builder.setWhen(System.currentTimeMillis()).setPriority(Notification.PRIORITY_DEFAULT).setOngoing(false).setSmallIcon(R.mipmap.ic_burger).setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_burger)).setAutoCancel(true).setContentTitle("看完这篇文章，你的Linux基础就差不多了").setContentText("内核(kernel)是系统的心脏，是运行程序和管理像磁盘和打印机等硬件设备的核心程序，它提供了一个在裸设备与应用程序间的抽象层");
        NotificationCompat.BigPictureStyle style = new NotificationCompat.BigPictureStyle();
        style.bigPicture(BitmapFactory.decodeResource(getResources(), R.mipmap.bg_5));
        builder.setStyle(style);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_IMMUTABLE);
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
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        builder.setContentIntent(pendingIntent);
        notification.getNotificationManager().notify(notifyId++, builder.build());
    }

    private void sendBigTextStyle() {
        notification.createNotificationChannel();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NotificationHelper.CHANNEL_ID);
        builder.setWhen(System.currentTimeMillis()).setPriority(Notification.PRIORITY_DEFAULT).setOngoing(false).setSmallIcon(R.mipmap.ic_burger).setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_burger)).setAutoCancel(true).setContentTitle("看完这篇文章，你的Linux基础就差不多了").setContentText("内核(kernel)是系统的心脏，是运行程序和管理像磁盘和打印机等硬件设备的核心程序，它提供了一个在裸设备与应用程序间的抽象层");
        builder.setStyle(new NotificationCompat.BigTextStyle());
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        builder.setContentIntent(pendingIntent);
        notification.getNotificationManager().notify(notifyId++, builder.build());
    }

    private void sendCustomLayout() {
        notification.createNotificationChannel();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NotificationHelper.CHANNEL_ID);
        builder.setWhen(System.currentTimeMillis()).setPriority(Notification.PRIORITY_DEFAULT).setOngoing(false).setSmallIcon(R.mipmap.ic_burger).setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_burger)).setAutoCancel(true).setContentTitle("自定义布局title").setContentText("自定义布局text");
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.layout_notification_custom);
        builder.setCustomBigContentView(remoteViews);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        builder.setContentIntent(pendingIntent);
        notification.getNotificationManager().notify(notifyId++, builder.build());
    }

    private void sendNormal() {
        notification.send(new NotificationBean.Builder().title("看完这篇文章，你的Linux基础就差不多了").summary("内核(kernel)是系统的心脏，是运行程序和管理像磁盘和打印机等硬件设备的核心程序，它提供了一个在裸设备与应用程序间的抽象层").build());
    }
}
