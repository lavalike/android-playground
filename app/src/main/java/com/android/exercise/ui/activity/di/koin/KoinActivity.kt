package com.android.exercise.ui.activity.di.koin

import android.os.Bundle
import com.android.exercise.R
import com.android.exercise.base.BaseActivity
import com.android.exercise.base.toolbar.ToolbarFactory
import com.android.exercise.databinding.ActivityKoinBinding

/**
 * KoinActivity
 * @author: zhen51.wang
 * @date: 2022/11/9/009
 */
class KoinActivity : BaseActivity() {
    lateinit var binding: ActivityKoinBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKoinBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun createToolbar() = ToolbarFactory.themed(this, getString(R.string.item_koin))
}