package com.android.exercise.ui.activity.ipc.messenger

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import com.android.exercise.R
import com.android.exercise.base.BaseActivity
import com.android.exercise.base.toolbar.ToolBarCommonHolder
import com.android.exercise.util.L

/**
 * messenger activity
 * Created by wangzhen on 2020/10/24.
 */
class MessengerActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_messenger)

        val intent = Intent(this, MessengerService::class.java)
        bindService(intent, connection, Context.BIND_AUTO_CREATE)
    }

    override fun onSetupToolbar(toolbar: Toolbar?, actionBar: ActionBar?) {
        ToolBarCommonHolder(this, toolbar, getString(R.string.item_messenger))
    }

    override fun onDestroy() {
        unbindService(connection)
        super.onDestroy()
    }

    private val connection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            val messenger = Messenger(service)

            val message = Message.obtain(null, 0x1)
            message.data = Bundle().apply {
                putString("msg", "Hello from Client")
            }

            // 设置服务端回复消息所用的Messenger
            message.replyTo = Messenger(handler)

            // 发送消息
            messenger.send(message)
        }

        override fun onServiceDisconnected(name: ComponentName) {
            L.e("-> onServiceDisconnected")
        }
    }

    private val handler: Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                0x2 -> {
                    L.e("-> client receive : ${msg.data.getString("msg")}")
                }
            }
        }
    }
}