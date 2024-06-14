package com.android.playground.base.toolbar

import android.app.Activity
import android.view.View
import com.android.playground.R
import com.android.playground.databinding.ToolbarThemedCommonBinding
import com.wangzhen.commons.toolbar.impl.Toolbar

/**
 * ThemedCommonToolbar
 * @author: zhen51.wang
 * @date: 2022/10/31/031
 */
class ThemedCommonToolbar(activity: Activity, val title: String) : Toolbar(activity) {
    override fun layoutRes(): Int = R.layout.toolbar_themed_common

    override fun onViewCreated(view: View) {
        ToolbarThemedCommonBinding.bind(view).apply {
            tvTitle.text = title
            btnBack.setOnClickListener { activity.finish() }
        }
    }
}