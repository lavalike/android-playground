package com.android.playground.ui.activity.hook

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wangzhen.adapter.RecyclerAdapter

/**
 * HookAdapter
 * Created by wangzhen on 2020/8/13.
 */
class HookAdapter(val list: List<Int>) : RecyclerAdapter<Int>(list) {
    override fun onAbsCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return HookViewHolder(parent)
    }

}