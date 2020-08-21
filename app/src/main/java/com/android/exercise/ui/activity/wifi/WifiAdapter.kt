package com.android.exercise.ui.activity.wifi

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dimeno.adapter.RecyclerAdapter

/**
 * WifiAdapter
 * Created by wangzhen on 2020/8/21.
 */
class WifiAdapter(val list: MutableList<WifiEntity>) : RecyclerAdapter<WifiEntity>(list) {
    override fun onAbsCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return WifiViewHolder(parent)
    }
}