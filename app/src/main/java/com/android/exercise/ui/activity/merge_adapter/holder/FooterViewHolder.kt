package com.android.exercise.ui.activity.merge_adapter.holder

import android.view.ViewGroup
import com.android.exercise.R
import com.dimeno.adapter.base.RecyclerViewHolder
import kotlinx.android.synthetic.main.item_merge_footer_layout.view.*

/**
 * FooterViewHolder
 * Created by wangzhen on 2020/7/18.
 */
class FooterViewHolder(parent: ViewGroup) : RecyclerViewHolder<String?>(parent, R.layout.item_merge_footer_layout) {
    override fun bind() {
        itemView.tv_footer.text = mData
    }

}