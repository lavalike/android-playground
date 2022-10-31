package com.android.exercise.ui.activity.motion

import android.os.Bundle
import com.android.exercise.base.BaseActivity
import com.android.exercise.databinding.ActivityCoordinateMotionBinding
import com.google.android.material.appbar.AppBarLayout

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