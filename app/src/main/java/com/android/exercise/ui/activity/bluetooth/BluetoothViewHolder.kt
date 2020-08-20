package com.android.exercise.ui.activity.bluetooth

import android.view.ViewGroup
import com.android.exercise.R
import com.dimeno.adapter.base.RecyclerViewHolder
import kotlinx.android.synthetic.main.item_bluetooth_holder_layout.view.*

/**
 * BluetoothViewHolder
 * Created by wangzhen on 2020/8/20.
 */
class BluetoothViewHolder(val parent: ViewGroup) : RecyclerViewHolder<BluetoothEntity>(parent, R.layout.item_bluetooth_holder_layout) {
    override fun bind() {
        itemView.tv_name.text = mData.name
        itemView.tv_address.text = mData.address
    }
}