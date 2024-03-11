package com.android.exercise.ui.activity.innovation.playback

import android.os.Bundle
import android.view.MotionEvent
import com.android.exercise.R
import com.android.exercise.base.BaseActivity
import com.android.exercise.base.toolbar.ToolbarFactory
import com.android.exercise.databinding.ActivityEventPlaybackBinding

/**
 * 基于Activity事件的录制与回放
 *
 * @author : wangzhen
 * @date : 2024/3/8/008
 */
class EventPlaybackActivity : BaseActivity() {
    private lateinit var binding: ActivityEventPlaybackBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventPlaybackBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setEvents()
    }

    private fun setEvents() {
        with(binding) {
            btnStart.setOnClickListener { EventRecorder.startRecord() }
            btnStop.setOnClickListener { EventRecorder.stopRecord() }
            btnPlayback.setOnClickListener {
                clearText()
                EventRecorder.playback(this@EventPlaybackActivity)
            }
            btnStopPlayback.setOnClickListener { EventRecorder.stopPlayback() }
            btnOne.setOnClickListener { appendText("1") }
            btnTwo.setOnClickListener { appendText("2") }
            btnThree.setOnClickListener { appendText("3") }
            btnFour.setOnClickListener { appendText("4") }
        }
    }

    private fun clearText() {
        binding.tvValue.text = ""
    }

    private fun appendText(text: String) {
        with(binding) {
            val builder = StringBuilder(tvValue.text)
            if (builder.isNotEmpty()) {
                builder.append("、")
            }
            builder.append(text)
            tvValue.text = builder.toString()
        }
    }

    override fun createToolbar() =
        ToolbarFactory.themed(this, getString(R.string.item_event_playback))

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        EventRecorder.dispatchTouchEvent(ev)
        return super.dispatchTouchEvent(ev)
    }

    override fun onDestroy() {
        EventRecorder.clear()
        super.onDestroy()
    }
}