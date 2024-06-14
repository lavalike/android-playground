package com.android.playground.ui.activity.ipc.messenger

import android.app.Service
import android.content.Intent
import android.os.*
import com.android.playground.util.L

/**
 * MessengerService
 * Created by wangzhen on 2020/10/24.
 */
class MessengerService : Service() {
    override fun onBind(p0: Intent?): IBinder? {
        return Messenger(handler).binder
    }

    private val handler: Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                0x1 -> {
                    // 收到消息
                    L.e("-> server receive : ${msg.data.getString("msg")}")

                    // 回复消息
                    msg.replyTo?.let { messenger ->
                        val message = Message.obtain(null, 0x2)
                        message.data = Bundle().apply {
                            putString("msg", "Hello from service")
                        }
                        messenger.send(message)
                    }
                }
            }
        }
    }
}