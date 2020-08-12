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
        hookOnClickListener(btn_normal)

        btn_long_click.setOnLongClickListener {
            Toast.makeText(this, "normal long click", Toast.LENGTH_SHORT).show()
            false
        }

        hookOnLongClickListener(btn_long_click)
    }

    override fun onSetupToolbar(toolbar: Toolbar?, actionBar: ActionBar?) {
        ToolBarCommonHolder(this, toolbar, getString(R.string.item_hook))
    }

    private fun hookOnLongClickListener(view: View) {
        //拿到mListenerInfo
        val method: Method = View::class.java.getDeclaredMethod("getListenerInfo")
        method.isAccessible = true
        val mListenerInfo = method.invoke(view)
        //mOnLongClickListener
        val field = Class.forName("android.view.View\$ListenerInfo").getDeclaredField("mOnLongClickListener")
        field.isAccessible = true
        val listener = field.get(mListenerInfo)
        if (listener != null) {
            field.set(mListenerInfo, HookLongClickListener(listener as View.OnLongClickListener))
        }
    }

    private fun hookOnClickListener(view: View) {
        //拿到mListenerInfo
        val method: Method = View::class.java.getDeclaredMethod("getListenerInfo")
        method.isAccessible = true
        val mListenerInfo = method.invoke(view)
        //拿到mOnClickListener
        val field = Class.forName("android.view.View\$ListenerInfo").getDeclaredField("mOnClickListener")
        val listener = field.get(mListenerInfo)
        if (listener != null) {
            field.set(mListenerInfo, HookClickListener(listener as View.OnClickListener))
        }
    }

    class HookLongClickListener(private val original: View.OnLongClickListener) : View.OnLongClickListener {
        override fun onLongClick(v: View): Boolean {
            Toast.makeText(v.context, "OnLongClick Hooked!", Toast.LENGTH_SHORT).show()
            return original.onLongClick(v)
        }

    }

    class HookClickListener(private val original: View.OnClickListener) : View.OnClickListener {
        override fun onClick(v: View) {
            Toast.makeText(v.context, "OnClick Hooked!", Toast.LENGTH_SHORT).show()
            original.onClick(v)
        }
    }
}