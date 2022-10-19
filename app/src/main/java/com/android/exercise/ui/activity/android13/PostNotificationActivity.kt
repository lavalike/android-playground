package com.android.exercise.ui.activity.android13

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import androidx.core.app.NotificationCompat
import com.android.exercise.R
import com.android.exercise.base.BaseActivity
import com.android.exercise.base.toolbar.ToolBarCommonHolder
import com.android.exercise.databinding.ActivityPostNotificationBinding

/**
 * PostNotificationActivity
 * @author: zhen51.wang
 * @date: 2022/10/19/019
 */
class PostNotificationActivity : BaseActivity() {
    companion object {
        const val CHANNEL_ID = "id"
        const val CHANNEL_NAME = "name"

        var notifyId = 0
    }

    private lateinit var binding: ActivityPostNotificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        with(binding) {
            btnPost.setOnClickListener {
                sendNotification()
            }
        }
    }

    private fun sendNotification() {
        val manager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        // create channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            manager.createNotificationChannel(NotificationChannel(
                CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                enableLights(true)
                setShowBadge(true)
                enableVibration(true)
                lightColor = Color.RED
                lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            })
        }
        // create notification
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setWhen(System.currentTimeMillis())
            .setPriority(NotificationManager.IMPORTANCE_DEFAULT)
            .setOngoing(false)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setLargeIcon(BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher))
            .setAutoCancel(true)
            .setContentTitle("post notification")
            .setContentText("msg comes: ${System.currentTimeMillis()}")
        manager.notify(notifyId++, builder.build())
    }


    override fun onSetupToolbar(toolbar: Toolbar?, actionBar: ActionBar?) {
        ToolBarCommonHolder(this, toolbar, getString(R.string.item_android13_post_notification))
    }
}