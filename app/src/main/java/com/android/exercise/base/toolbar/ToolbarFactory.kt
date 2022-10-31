package com.android.exercise.base.toolbar

import android.app.Activity
import com.wangzhen.commons.toolbar.impl.Toolbar

/**
 * ToolbarFactory
 * @author: zhen51.wang
 * @date: 2022/10/31/031
 */
class ToolbarFactory {
    companion object {
        @JvmStatic
        fun themed(activity: Activity, title: String): Toolbar {
            return ThemedCommonToolbar(activity, title)
        }
    }
}