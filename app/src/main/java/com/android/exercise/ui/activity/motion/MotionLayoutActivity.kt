package com.android.exercise.ui.activity.motion

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import com.android.exercise.R
import com.android.exercise.base.BaseActivity
import com.android.exercise.base.toolbar.ToolBarCommonHolder
import com.android.exercise.databinding.ActivityMotionLayoutBinding

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

    override fun onSetupToolbar(toolbar: Toolbar?, actionBar: ActionBar?) {
        ToolBarCommonHolder(this, toolbar, getString(R.string.item_motion_layout))
    }
}