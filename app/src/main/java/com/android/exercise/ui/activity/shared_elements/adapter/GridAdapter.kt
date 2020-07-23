package com.android.exercise.ui.activity.shared_elements.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.exercise.ui.activity.shared_elements.entity.GridEntity
import com.android.exercise.ui.activity.shared_elements.holder.GridViewHolder
import com.dimeno.adapter.RecyclerAdapter

/**
 * GridAdapter
 * Created by wangzhen on 2020/7/20.
 */
class GridAdapter(list: List<GridEntity>) : RecyclerAdapter<GridEntity>(list) {
    override fun onAbsCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return GridViewHolder(parent)
    }
}