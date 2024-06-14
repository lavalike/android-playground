package com.android.playground.ui.activity.view.typewriter

import android.os.Bundle
import com.android.playground.R
import com.android.playground.base.BaseActivity
import com.android.playground.base.toolbar.ToolbarFactory
import com.android.playground.databinding.ActivityTypewriterBinding

/**
 * TypewriterActivity
 * @author: zhen51.wang
 * @date: 2022/11/14/014
 */
class TypewriterActivity : BaseActivity() {
    private lateinit var binding: ActivityTypewriterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTypewriterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        with(binding) {
            banner.callback {
                left.textSize(30).callback {
                    right.textSize(30).update("春风送暖入屠苏")
                }.update("爆竹声中一岁除")
            }.update("新年快乐")
        }
    }

    override fun createToolbar() = ToolbarFactory.themed(this, getString(R.string.item_typewriter))
}