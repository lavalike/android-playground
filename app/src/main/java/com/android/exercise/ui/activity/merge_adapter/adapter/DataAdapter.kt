package com.android.exercise.ui.activity.merge_adapter.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.exercise.ui.activity.merge_adapter.holder.DataViewHolder
import com.dimeno.adapter.RecyclerAdapter

/**
 * DataAdapter
 * Created by wangzhen on 2020/7/18.
 */
class DataAdapter(list: List<String>) : RecyclerAdapter<String>(list) {
    override fun onAbsCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return DataViewHolder(parent)
    }

}