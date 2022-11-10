package com.android.exercise.ui.activity.di.koin

import android.os.Bundle
import com.android.exercise.R
import com.android.exercise.base.BaseActivity
import com.android.exercise.base.toolbar.ToolbarFactory
import com.android.exercise.databinding.ActivityKoinBinding
import com.android.exercise.ui.activity.di.koin.service.UserService
import org.koin.android.ext.android.inject

/**
 * KoinActivity
 * @author: zhen51.wang
 * @date: 2022/11/9/009
 */
class KoinActivity : BaseActivity() {
    lateinit var binding: ActivityKoinBinding
    // way 1
    // private val service: UserService by inject()

    // way 2
    private val service by inject<UserService>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKoinBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tv.text = service.say()
    }

    override fun createToolbar() = ToolbarFactory.themed(this, getString(R.string.item_koin))
}