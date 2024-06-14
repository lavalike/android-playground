package com.android.playground.ui.activity.motion

import android.os.Bundle
import com.android.playground.base.BaseActivity
import com.android.playground.databinding.ActivityCoordinateMotionBinding

/**
 * CoordinateMotionActivity
 * Created by wangzhen on 2021/9/3
 */
class CoordinateMotionActivity : BaseActivity() {
    private lateinit var binding: ActivityCoordinateMotionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityCoordinateMotionBinding.inflate(layoutInflater).apply {
            binding = this
        }.root)

        binding.back.setOnClickListener { finish() }

        binding.appbar.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
            val seekPosition = -verticalOffset / (appBarLayout?.totalScrollRange!!.toFloat())
            binding.motion.progress = seekPosition
        }
    }
}