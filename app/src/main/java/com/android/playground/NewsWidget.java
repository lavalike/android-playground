package com.android.playground;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.Random;

/**
 * 新闻小插件
 * Created by wangzhen on 2018/10/11.
 */
public class NewsWidget extends AppWidgetProvider {

    static final String ACTION = "com.exercise.widget";
    static String title = "习近平出席中法全球理论坛闭幕式并致辞";
    static String tag = "活动";
    static String column = "杭州吃货";
    static String read = "1005阅读";
    static String time = "1小时前";
    static int[] resIds = new int[]{R.mipmap.bg_dog, R.mipmap.bg_1, R.mipmap.bg_2, R.mipmap.bg_3, R.mipmap.bg_4, R.mipmap.bg_5, R.mipmap.bg_6};

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        appWidgetManager.updateAppWidget(appWidgetId, getRemoteViews(context));
    }

    @NonNull
    private static RemoteViews getRemoteViews(Context context) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
        views.setTextViewText(R.id.tv_title, title);
        views.setTextViewText(R.id.tv_tag, tag);
        views.setTextViewText(R.id.tv_column, column);
        views.setTextViewText(R.id.tv_read, read);
        views.setTextViewText(R.id.tv_time, time);
        views.setImageViewResource(R.id.imageView, resIds[new Random().nextInt(resIds.length)]);

        Intent intent = new Intent(context, NewsWidget.class);
        intent.setAction(ACTION);
        views.setOnClickPendingIntent(R.id.imageView, PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_IMMUTABLE));
        return views;
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
        Toast.makeText(context, "新闻插件已启用", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
        Toast.makeText(context, "新闻插件已移除", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (ACTION.equals(intent.getAction())) {
            RemoteViews remoteViews = getRemoteViews(context);
            AppWidgetManager manager = AppWidgetManager.getInstance(context);
            ComponentName componentName = new ComponentName(context, NewsWidget.class);
            manager.updateAppWidget(componentName, remoteViews);
        }
    }
}

