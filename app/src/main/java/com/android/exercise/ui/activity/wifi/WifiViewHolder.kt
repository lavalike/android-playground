package com.android.exercise.ui.activity.wifi

import android.view.ViewGroup
import com.android.exercise.R
import com.dimeno.adapter.base.RecyclerViewHolder
import kotlinx.android.synthetic.main.item_wifi_holder_layout.view.*

/**
 * WifiViewHolder
 * Created by wangzhen on 2020/8/21.
 */
class WifiViewHolder(val parent: ViewGroup) : RecyclerViewHolder<WifiEntity>(parent, R.layout.item_wifi_holder_layout) {
    override fun bind() {
        itemView.tv_name.text = String.format("名称：%s", if (mData.isCurrent) "${mData.ssid}(已连接)" else mData.ssid)
        itemView.tv_address.text = String.format("BSSID：%s", mData.bssid)
        itemView.tv_frequency.text = String.format("频率：%s", "${mData.frequency}MHz")
    }
}