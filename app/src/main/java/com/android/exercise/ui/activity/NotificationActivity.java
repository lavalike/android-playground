package com.android.exercise.ui.activity;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RemoteViews;

import com.android.exercise.R;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.base.manager.NotificationHelper;
import com.android.exercise.base.toolbar.ToolBarCommonHolder;
import com.android.exercise.domain.NotificationBean;
import com.android.exercise.listener.INotification;
import com.android.exercise.ui.MainActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class NotificationActivity extends BaseActivity {

    private INotification notification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        ButterKnife.bind(this);
        notification = NotificationHelper.get(this);
    }

    @Override
    protected void onSetupToolbar(Toolbar toolbar, ActionBar actionBar) {
        new ToolBarCommonHolder(this, toolbar, getString(R.string.item_notification));
    }

    @OnClick({R.id.btn_send_normal, R.id.btn_send_custom})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_send_normal:
                sendNormal();
                break;
            case R.id.btn_send_custom:
                sendCustom();
                break;
        }
    }

    private void sendCustom() {
        notification.createNotificationChannel();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NotificationHelper.CHANNEL_ID);
        builder.setWhen(System.currentTimeMillis())
                .setPriority(Notification.PRIORITY_DEFAULT)
                .setOngoing(false)
                .setSmallIcon(R.mipmap.ic_burger)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_burger))
                .setAutoCancel(true);
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.layout_notification_custom);
        builder.setContent(remoteViews);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setContentIntent(pendingIntent);
        notification.getNotificationManager().notify(100, builder.build());
    }

    private void sendNormal() {
        notification.send(new NotificationBean.Builder()
                .setTitle("看完这篇文章，你的Linux基础就差不多了")
                .setSummary("内核(kernel)是系统的心脏，是运行程序和管理像磁盘和打印机等硬件设备的核心程序，它提供了一个在裸设备与应用程序间的抽象层")
                .build());
    }
}
