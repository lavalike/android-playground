package com.android.exercise.base.toolbar

import android.app.Activity
import android.view.View
import com.android.exercise.R
import com.android.exercise.databinding.ToolbarThemedMenuTextBinding
import com.wangzhen.commons.toolbar.impl.Toolbar

/**
 * ThemedMenuTextToolbar
 * @author: zhen51.wang
 * @date: 2022/10/31/031
 */
class ThemedMenuTextToolbar(
    activity: Activity, val title: String, private val menu: String, private val callback: Callback?
) : Toolbar(activity) {
    private lateinit var binding: ToolbarThemedMenuTextBinding

    override fun layoutRes(): Int = R.layout.toolbar_themed_menu_text

    override fun onViewCreated(view: View) {
        ToolbarThemedMenuTextBinding.bind(view).apply {
            binding = this
            btnBack.setOnClickListener { activity.finish() }
            tvTitle.text = title
            btnMenu.text = menu
            btnMenu.setOnClickListener {
                callback?.onClick()
            }
        }
    }

    fun setTitle(title: String) {
        binding.tvTitle.text = title
    }

    interface Callback {
        fun onClick()
    }
}