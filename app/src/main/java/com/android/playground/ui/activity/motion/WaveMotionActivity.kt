package com.android.playground.ui.activity.motion

import android.os.Bundle
import com.android.playground.base.BaseActivity
import com.android.playground.databinding.ActivityWaveMotionBinding

/**
 * WaveMotionActivity
 * Created by wangzhen on 2021/9/2
 */
class WaveMotionActivity : BaseActivity() {
    private lateinit var binding: ActivityWaveMotionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fitDarkStatus(true)
        setContentView(ActivityWaveMotionBinding.inflate(layoutInflater).apply {
            binding = this
        }.root)
    }
}