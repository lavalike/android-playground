package com.android.exercise.base

import android.content.Context
import com.wangzhen.commons.toolbar.ToolbarActivity
import android.os.Bundle
import com.android.exercise.base.manager.AppManager
import android.widget.Toast
import com.wangzhen.statusbar.DarkStatusBar

/**
 * Activity基类
 * Created by Administrator on 2016/4/12.
 */
abstract class BaseActivity : ToolbarActivity() {
    @JvmField
    var mContext: Context? = null

    @JvmField
    protected var tag: String = javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        AppManager.get().addActivity(this)
    }

    protected fun showToast(text: String?) {
        Toast.makeText(mContext, text, Toast.LENGTH_SHORT).show()
    }

    fun fitDarkStatus(isDark: Boolean) {
        if (isDark) DarkStatusBar.get().fitDark(this) else DarkStatusBar.get().fitLight(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        AppManager.get().removeActivity(this)
    }
}