package com.android.exercise.ui.activity.bluetooth

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dimeno.adapter.RecyclerAdapter

/**
 * BluetoothAdapter
 * Created by wangzhen on 2020/8/20.
 */
class DataAdapter(val list: MutableList<BluetoothEntity>?) : RecyclerAdapter<BluetoothEntity>(list) {
    override fun onAbsCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return BluetoothViewHolder(parent)
    }
}