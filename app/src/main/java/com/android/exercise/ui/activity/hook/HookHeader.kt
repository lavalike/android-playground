package com.android.exercise.ui.activity.hook

import android.view.View
import android.widget.Toast
import com.android.exercise.R
import com.dimeno.adapter.base.RecyclerItem
import kotlinx.android.synthetic.main.hook_header_layout.view.*
import java.lang.reflect.Method

/**
 * HookHeader
 * Created by wangzhen on 2020/8/13.
 */
class HookHeader : RecyclerItem() {
    override fun layout(): Int {
        return R.layout.hook_header_layout
    }

    override fun onViewCreated(itemView: View) {
        itemView.btn_normal.setOnClickListener {
            Toast.makeText(itemView.context, "normal click", Toast.LENGTH_SHORT).show()
        }
        hookOnClickListener(itemView.btn_normal)

        itemView.btn_long_click.setOnLongClickListener {
            Toast.makeText(itemView.context, "normal long click", Toast.LENGTH_SHORT).show()
            false
        }

        hookOnLongClickListener(itemView.btn_long_click)
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