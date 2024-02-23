package com.android.exercise.ui.activity.android14

import android.Manifest
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import com.android.exercise.base.BaseActivity
import com.android.exercise.base.manager.NotificationHelper
import com.android.exercise.base.toolbar.ToolbarFactory
import com.android.exercise.databinding.ActivityFullscreenNotificationBinding
import com.android.exercise.domain.NotificationBean
import com.android.exercise.util.toast
import com.wangzhen.permission.PermissionManager
import com.wangzhen.permission.callback.AbsPermissionCallback

/**
 * FullscreenNotificationActivity
 *
 * @author : wangzhen
 * @date : 2024/2/23/023
 */
class FullscreenNotificationActivity : BaseActivity() {
    private lateinit var binding: ActivityFullscreenNotificationBinding
    private val manager by lazy {
        getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFullscreenNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setEvents()
    }

    private fun setEvents() {
        with(binding) {
            btnRequestPermission.setOnClickListener {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    PermissionManager.request(it.context, object : AbsPermissionCallback() {
                        override fun onDeny(
                            deniedPermissions: Array<String>, neverAskPermissions: Array<String>
                        ) {
                            "permission denied".toast()
                        }

                        override fun onGrant(permissions: Array<String>) {
                            "permission granted".toast()
                        }
                    }, Manifest.permission.POST_NOTIFICATIONS)
                } else {
                    "POST_NOTIFICATIONS Supported Since TIRAMISU".toast()
                }
            }
            btnSettings.setOnClickListener {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                    fullScreenLauncher.launch(Intent(Settings.ACTION_MANAGE_APP_USE_FULL_SCREEN_INTENT).apply {
                        data = Uri.fromParts("package", packageName, null)
                    })
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    "FullScreen Notification Enabled".toast()
                } else {
                    "FullScreen Notification Not Supported".toast()
                }
            }
            btnSend.setOnClickListener {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                    if (manager.canUseFullScreenIntent()) {
                        sendFullScreenNotification()
                    } else {
                        "Enable FullScreen Notification First".toast()
                    }
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    sendFullScreenNotification()
                } else {
                    "FullScreen Notification Not Supported".toast()
                }
            }
        }
    }

    @RequiresApi(34)
    private val fullScreenLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (manager.canUseFullScreenIntent()) {
            "FullScreen Notification Enabled".toast()
        } else {
            "FullScreen Notification Disabled".toast()
        }
    }

    private fun sendFullScreenNotification() {
        NotificationHelper.getInstance(this).send(
            NotificationBean.Builder().title("全屏通知").content("全屏通知")
                .summary("收到一条全屏通知").fullScreen(true).build()
        )

    }

    override fun createToolbar() = ToolbarFactory.themed(this, "全屏通知")
}