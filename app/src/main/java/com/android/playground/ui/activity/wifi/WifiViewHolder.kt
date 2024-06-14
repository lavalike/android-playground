package com.android.playground.ui.activity.wifi

import android.view.ViewGroup
import com.android.playground.R
import com.android.playground.databinding.ItemWifiHolderLayoutBinding
import com.wangzhen.adapter.base.RecyclerViewHolder

/**
 * WifiViewHolder
 * Created by wangzhen on 2020/8/21.
 */
class WifiViewHolder(val parent: ViewGroup) :
    RecyclerViewHolder<WifiEntity>(parent, R.layout.item_wifi_holder_layout) {
    val binding = ItemWifiHolderLayoutBinding.bind(itemView)
    override fun bind() {
        binding.tvName.text =
            String.format("名称：%s", if (mData.isCurrent) "${mData.ssid}(已连接)" else mData.ssid)
        binding.tvAddress.text = String.format("BSSID：%s", mData.bssid)
        binding.tvFrequency.text = String.format("频率：%s", "${mData.frequency}MHz")
    }
}