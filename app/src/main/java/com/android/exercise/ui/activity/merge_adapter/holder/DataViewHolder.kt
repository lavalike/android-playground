package com.android.exercise.ui.activity.merge_adapter.holder

import android.view.ViewGroup
import com.android.exercise.R
import com.dimeno.adapter.base.RecyclerViewHolder
import kotlinx.android.synthetic.main.item_merge_data_layout.view.*

/**
 * DataViewHolder
 * Created by wangzhen on 2020/7/18.
 */
class DataViewHolder(parent: ViewGroup) : RecyclerViewHolder<String>(parent, R.layout.item_merge_data_layout) {
    override fun bind() {
        itemView.tv_data.text = mData
    }

}