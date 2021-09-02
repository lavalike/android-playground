package com.android.exercise.ui.activity.motion

import android.os.Bundle
import com.android.exercise.base.BaseActivity
import com.android.exercise.databinding.ActivityWaveMotionBinding

/**
 * WaveMotionActivity
 * Created by wangzhen on 2021/9/2
 */
class WaveMotionActivity : BaseActivity() {
    private lateinit var binding: ActivityWaveMotionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityWaveMotionBinding.inflate(layoutInflater).apply {
            binding = this
        }.root)
    }

    override fun showToolbar(): Boolean {
        return false
    }
}