package com.android.exercise.ui.activity.kotlin

import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import com.android.exercise.R
import com.android.exercise.base.BaseActivity
import com.android.exercise.base.toolbar.ToolBarCommonHolder
import com.android.exercise.util.L
import kotlinx.android.synthetic.main.activity_coroutines.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

/**
 * CoroutinesActivity
 * Created by wangzhen on 2020/10/15.
 */
class CoroutinesActivity : BaseActivity() {
    private val mainScope = MainScope()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coroutines)

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
            tv.text = String.format("%d * %d = %d", num, num, result)
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

    override fun onSetupToolbar(toolbar: Toolbar?, actionBar: ActionBar?) {
        ToolBarCommonHolder(this, toolbar, getString(R.string.item_kotlin_coroutines))
    }

    override fun onDestroy() {
        super.onDestroy()
        mainScope.destroy()
    }
}