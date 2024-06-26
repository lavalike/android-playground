package com.android.playground.ui.activity.motion

import android.content.Intent
import android.os.Bundle
import com.android.playground.R
import com.android.playground.base.BaseActivity
import com.android.playground.base.toolbar.ToolbarFactory
import com.android.playground.databinding.ActivityMotionLayoutBinding
import com.wangzhen.commons.toolbar.impl.Toolbar

/**
 * MotionLayoutActivity
 * Created by wangzhen on 2021/9/2
 */
class MotionLayoutActivity : BaseActivity() {
    private lateinit var binding: ActivityMotionLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityMotionLayoutBinding.inflate(layoutInflater).apply {
            binding = this
        }.root)

        binding.btnLoadingBall.setOnClickListener {
            startActivity(Intent(this, LoadingBallActivity::class.java))
        }
        binding.btnWave.setOnClickListener {
            startActivity(Intent(this, WaveMotionActivity::class.java))
        }
        binding.btnCoordinateLayout.setOnClickListener {
            startActivity(Intent(this, CoordinateMotionActivity::class.java))
        }
    }

    override fun createToolbar(): Toolbar {
        return ToolbarFactory.themed(this, getString(R.string.item_motion_layout))
    }
}