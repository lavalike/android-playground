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
    private val touchEvents = LinkedList<EventData>()

    private var isRecording = false
    private var isPlaying = false
    private var startTime = 0L

    private val handler = Handler(Looper.getMainLooper())

    fun hasEvents() = touchEvents.isNotEmpty()

    fun startRecord() {
        if (isRecording) return
        isRecording = true
        startTime = System.currentTimeMillis()
        touchEvents.clear()
    }

    fun stopRecord() {
        if (isRecording) {
            isRecording = false
        }
    }

    @SuppressLint("Recycle")
    fun dispatchTouchEvent(ev: MotionEvent?) {
        if (!isRecording) return
        // 原始Event用完即回收，此处伪造一个
        val obtain = MotionEvent.obtain(ev)
        // 当前时间与开启录制的时间间隔
        val delay = System.currentTimeMillis() - startTime
        touchEvents.add(EventData(event = obtain, delay = delay))
    }

    fun playback(activity: Activity) {
        if (isRecording) return
        isPlaying = true
        touchEvents.forEach { event ->
            handler.postDelayed({
                activity.dispatchTouchEvent(event.event)
            }, event.delay)
        }
    }

    fun stopPlayback() {
        isPlaying = false
        handler.removeCallbacksAndMessages(null)
    }

    fun clear() {
        stopRecord()
        stopPlayback()
        touchEvents.forEach {
            it.event?.recycle()
        }
        touchEvents.clear()
    }
}