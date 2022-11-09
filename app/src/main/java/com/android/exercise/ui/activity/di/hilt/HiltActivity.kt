package com.android.exercise.ui.activity.di.hilt

import android.os.Bundle
import com.android.exercise.R
import com.android.exercise.base.BaseActivity
import com.android.exercise.base.toolbar.ToolbarFactory
import com.android.exercise.databinding.ActivityHiltBinding
import com.wangzhen.commons.toolbar.impl.Toolbar

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