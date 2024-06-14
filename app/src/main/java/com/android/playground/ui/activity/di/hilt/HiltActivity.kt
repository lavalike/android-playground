package com.android.playground.ui.activity.di.hilt

import android.os.Bundle
import com.android.playground.R
import com.android.playground.base.BaseActivity
import com.android.playground.base.toolbar.ToolbarFactory
import com.android.playground.databinding.ActivityHiltBinding

/**
 * HiltActivity
 * @author: zhen51.wang
 * @date: 2022/11/9/009
 */
class HiltActivity : BaseActivity() {
    lateinit var binding: ActivityHiltBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHiltBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun createToolbar() = ToolbarFactory.themed(this, getString(R.string.item_hilt))
}