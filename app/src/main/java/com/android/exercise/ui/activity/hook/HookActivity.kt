package com.android.exercise.ui.activity.hook

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import com.android.exercise.R
import com.android.exercise.base.BaseActivity
import com.android.exercise.base.toolbar.ToolBarCommonHolder
import kotlinx.android.synthetic.main.activity_hook.*
import java.lang.reflect.Method

/**
 * HookActivity
 * Created by wangzhen on 2020/8/6.
 */
class HookActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hook)

        btn_normal.setOnClickListener {
            Toast.makeText(this, "normal click", Toast.LENGTH_SHORT).show()
        }
        hook()
    }

    override fun onSetupToolbar(toolbar: Toolbar?, actionBar: ActionBar?) {
        ToolBarCommonHolder(this, toolbar, getString(R.string.item_hook))
    }

    private fun hook() {
        //拿到mListenerInfo
        val method: Method = View::class.java.getDeclaredMethod("getListenerInfo")
        method.isAccessible = true
        val mListenerInfo = method.invoke(btn_normal)
        //拿到mOnClickListener
        val field = Class.forName("android.view.View\$ListenerInfo").getDeclaredField("mOnClickListener")
        val listener = field.get(mListenerInfo)
        if (listener != null) {
            field.set(mListenerInfo, HookClickListener(listener as View.OnClickListener))
        }
    }

    class HookClickListener(private val original: View.OnClickListener?) : View.OnClickListener {
        override fun onClick(v: View) {
            Toast.makeText(v.context, "Hooked !", Toast.LENGTH_SHORT).show()
            original?.onClick(v)
        }
    }
}