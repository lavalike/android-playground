package com.android.exercise.ui.activity.innovation.playback

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Handler
import android.os.Looper
import android.view.MotionEvent
import java.util.LinkedList

/**
 * EventRecorder
 *
 * @author : wangzhen
 * @date : 2024/3/8/008
 */
object EventRecorder {
    // 事件队列
    private val touchEvents = LinkedList<EventData>()

    private var isRecording = false
    private var isPlaying = false
    private var startTime = 0L

    private val handler = Handler(Looper.getMainLooper())

    fun hasEvents() = touchEvents.isNotEmpty()

    /**
     * 开始录制
     */
    fun startRecord() {
        // 录制状态判断
        if (isRecording) return
        isRecording = true
        // 记录开启录制时间
        startTime = System.currentTimeMillis()
        // 先清空事件列表
        touchEvents.clear()
    }

    // 停止录制
    fun stopRecord() {
        if (isRecording) {
            isRecording = false
        }
    }

    /**
     * 事件收集
     * @param ev MotionEvent?
     */
    @SuppressLint("Recycle")
    fun dispatchTouchEvent(ev: MotionEvent?) {
        if (!isRecording) return
        // 原始Event用完即回收，此处伪造一个
        val obtain = MotionEvent.obtain(ev)
        // 当前时间与开启录制的时间间隔
        val delay = System.currentTimeMillis() - startTime
        touchEvents.add(EventData(event = obtain, delay = delay))
    }

    /**
     * 事件回放
     * @param activity Activity
     */
    fun playback(activity: Activity) {
        // 回放状态判断，防止重复操作
        if (isRecording) return
        isPlaying = true
        // 遍历事件队列，回放事件
        touchEvents.forEach { event ->
            // 按指定时间间隔向Activity分发事件
            handler.postDelayed({
                activity.dispatchTouchEvent(event.event)
            }, event.delay)
        }
    }

    /**
     * 停止回放
     */
    fun stopPlayback() {
        isPlaying = false
        // 移除所有未执行的事件
        handler.removeCallbacksAndMessages(null)
    }

    /**
     * 清除事件（MotionEvent回收、缓存清理）
     */
    fun clear() {
        // 如果正在录制，则先停止录制
        stopRecord()
        // 如果正在回放，则先停止回放
        stopPlayback()
        touchEvents.forEach {
            // 回收伪造的事件
            it.event?.recycle()
        }
        // 清理缓存
        touchEvents.clear()
    }
}