package com.android.playground.ui.activity.coroutines

import android.os.Bundle
import com.android.playground.R
import com.android.playground.base.BaseActivity
import com.android.playground.base.toolbar.ToolbarFactory
import com.android.playground.databinding.ActivityCoroutinesBinding
import com.android.playground.util.L
import com.wangzhen.commons.toolbar.impl.Toolbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

/**
 * CoroutinesActivity
 * Created by wangzhen on 2020/10/15.
 */
class CoroutinesActivity : BaseActivity() {
    private lateinit var binding: ActivityCoroutinesBinding
    private val mainScope = MainScope()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoroutinesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /**
         * 调度器的作用是将协程限制在特定的线程执行。主要的调度器类型有：
         * Dispatchers.Main：指定执行的线程是主线程
         * Dispatchers.IO：指定执行的线程是 IO 线程
         * Dispatchers.Default：默认的调度器，适合执行 CPU 密集性的任务
         * Dispatchers.Unconfined：非限制的调度器，指定的线程可能会随着挂起的函数的发生变化
         */
        mainScope.launch(Dispatchers.Main) {
            val num = 20
            val result = getResult(20)
            binding.tv.text = String.format("%d * %d = %d", num, num, result)
        }

        mainScope.launch(Dispatchers.Main) {
            flow {
                for (i in 1..5) {
                    emit(i)
                }
            }.map {
                it * 2
            }.collect { num ->
                L.e("-> num $num ${Thread.currentThread().name}")
            }
        }

        mainScope.launch {
            // 发送数据

            // 方式一
            // 生成channel
            val channel = Channel<Int>()
            launch {
                for (i in 1..5) {
                    delay(1000)
                    channel.send(i)
                }
                channel.close()
            }

//            // 方式二
//            // experimental
//            val channel = produce {
//                for (i in 1..5) {
//                    delay(1000)
//                    send(i)
//                }
//                close()
//            }

            // 接收数据
            launch {
                for (i in channel) {
                    L.e("-> channel $i")
                }
            }
        }
    }

    private suspend fun getResult(num: Int): Int {
        delay(2000)
        return num * num
    }

    override fun createToolbar(): Toolbar {
        return ToolbarFactory.themed(this, getString(R.string.item_kotlin_coroutines))
    }

    override fun onDestroy() {
        super.onDestroy()
        mainScope.destroy()
    }
}