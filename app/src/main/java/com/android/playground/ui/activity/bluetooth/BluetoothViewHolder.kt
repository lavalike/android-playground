package com.android.playground.ui.activity.bluetooth

import android.view.ViewGroup
import com.android.playground.R
import com.android.playground.databinding.ItemBluetoothHolderLayoutBinding
import com.wangzhen.adapter.base.RecyclerViewHolder

/**
 * BluetoothViewHolder
 * Created by wangzhen on 2020/8/20.
 */
class BluetoothViewHolder(val parent: ViewGroup) :
    RecyclerViewHolder<BluetoothEntity>(parent, R.layout.item_bluetooth_holder_layout) {
    val binding = ItemBluetoothHolderLayoutBinding.bind(itemView)
    override fun bind() {
        binding.tvName.text = mData.name
        binding.tvAddress.text = mData.address
    }
}