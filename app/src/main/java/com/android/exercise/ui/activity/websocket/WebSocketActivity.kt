package com.android.exercise.ui.activity.websocket

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.widget.ScrollView
import com.android.exercise.R
import com.android.exercise.base.BaseActivity
import com.android.exercise.base.okhttp.OKHttpManager
import com.android.exercise.base.toolbar.ToolbarFactory
import com.android.exercise.databinding.ActivityWebSocketBinding
import com.wangzhen.commons.toolbar.impl.Toolbar
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import java.util.concurrent.Executors

/**
 * OkHttp—WebSocket长连接
 * Created by wangzhen on 2020/8/15.
 */
class WebSocketActivity : BaseActivity() {
    private lateinit var binding: ActivityWebSocketBinding
    private val mWebServer = MockWebServer()
    private var mClientSocket: WebSocket? = null
    private var mServerSocket: WebSocket? = null

    private var count = 0
    private val mHandler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                0x1 -> {
                    count++
                    mServerSocket?.send("服务端定时下发消息 $count")
                    sendEmptyMessageDelayed(0x1, 1000)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebSocketBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvMsg.text = "-------OkHttp 长连接-------\n"
        Executors.newCachedThreadPool().submit {
            initWebServer()
            initClient()
        }
        mHandler.sendEmptyMessageDelayed(0x1, 1000)
    }

    private fun initClient() {
        val url = "ws://" + mWebServer.hostName + ":" + mWebServer.port
        Log.e(tag, "initClient: $url")
        OKHttpManager.getClient()
            .newWebSocket(Request.Builder().url(url).build(), object : WebSocketListener() {
                override fun onOpen(webSocket: WebSocket, response: Response) {
                    super.onOpen(webSocket, response)
                    mClientSocket = webSocket
                    mHandler.post {
                        binding.tvMsg.append("客户端：连接成功\n")
                    }
                    webSocket.send("我是客户端，你好呀")
                }

                override fun onMessage(webSocket: WebSocket, text: String) {
                    super.onMessage(webSocket, text)
                    mHandler.post {
                        binding.tvMsg.append("客户端：$text\n")
                        binding.scroller.fullScroll(ScrollView.FOCUS_DOWN)
                    }
                }

                override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                    super.onClosed(webSocket, code, reason)
                    mClientSocket = null
                    mHandler.post {
                        binding.tvMsg.append("客户端：断开连接\n")
                    }
                }
            })
    }

    private fun initWebServer() {
        val response = MockResponse().withWebSocketUpgrade(object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                super.onOpen(webSocket, response)
                mServerSocket = webSocket
                mHandler.post {
                    binding.tvMsg.append("服务端：连接成功\n")
                }
                webSocket.send("我是服务器，你好呀")
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                super.onMessage(webSocket, text)
                mHandler.post {
                    binding.tvMsg.append("服务端：$text\n")
                }
            }

            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                super.onClosed(webSocket, code, reason)
                mServerSocket = null
                mHandler.post {
                    binding.tvMsg.append("服务端：断开连接\n")
                }

            }
        })
        mWebServer.enqueue(response)
    }

    override fun createToolbar(): Toolbar {
        return ToolbarFactory.themed(this, getString(R.string.item_okhttp_websocket))
    }

    override fun onDestroy() {
        super.onDestroy()
        mClientSocket?.close(3000, "client close")
        mServerSocket?.close(3000, "server close")
    }
}