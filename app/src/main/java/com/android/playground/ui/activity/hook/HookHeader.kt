package com.android.playground.ui.activity.hook

import android.view.View
import android.widget.Toast
import com.android.playground.R
import com.android.playground.databinding.HookHeaderLayoutBinding
import com.wangzhen.adapter.base.RecyclerItem
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
        val binding = HookHeaderLayoutBinding.bind(itemView)
        binding.btnNormal.setOnClickListener {
            Toast.makeText(itemView.context, "normal click", Toast.LENGTH_SHORT).show()
        }
        hookOnClickListener(binding.btnNormal)

        binding.btnLongClick.setOnLongClickListener {
            Toast.makeText(itemView.context, "normal long click", Toast.LENGTH_SHORT).show()
            false
        }

        hookOnLongClickListener(binding.btnLongClick)
    }

    private fun hookOnLongClickListener(view: View) {
        //拿到mListenerInfo
        val method: Method = View::class.java.getDeclaredMethod("getListenerInfo")
        method.isAccessible = true
        val mListenerInfo = method.invoke(view)
        //mOnLongClickListener
        val field = Class.forName("android.view.View\$ListenerInfo")
            .getDeclaredField("mOnLongClickListener")
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
        val field =
            Class.forName("android.view.View\$ListenerInfo").getDeclaredField("mOnClickListener")
        val listener = field.get(mListenerInfo)
        if (listener != null) {
            field.set(mListenerInfo, HookClickListener(listener as View.OnClickListener))
        }
    }

    class HookLongClickListener(private val original: View.OnLongClickListener) :
        View.OnLongClickListener {
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