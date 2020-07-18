package com.android.exercise.ui.activity.merge_adapter.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.exercise.ui.activity.merge_adapter.holder.FooterViewHolder
import com.dimeno.adapter.RecyclerAdapter

/**
 * FooterAdapter
 * Created by wangzhen on 2020/7/18.
 */
class FooterAdapter(list: MutableList<String>) : RecyclerAdapter<String>(list) {
    override fun onAbsCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return FooterViewHolder(parent)
    }

}