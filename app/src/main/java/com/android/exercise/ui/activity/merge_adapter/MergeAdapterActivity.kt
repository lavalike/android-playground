package com.android.exercise.ui.activity.merge_adapter

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.MergeAdapter
import com.android.exercise.R
import com.android.exercise.base.BaseActivity
import com.android.exercise.base.toolbar.ToolBarCommonHolder
import com.android.exercise.ui.activity.merge_adapter.adapter.DataAdapter
import com.android.exercise.ui.activity.merge_adapter.adapter.FooterAdapter
import com.android.exercise.ui.activity.merge_adapter.adapter.HeaderAdapter
import kotlinx.android.synthetic.main.activity_load_more.recycler
import kotlinx.android.synthetic.main.activity_merge_adapter.*

/**
 * MergeAdapter
 * Created by wangzhen on 2020/7/18.
 */
class MergeAdapterActivity : BaseActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_merge_adapter)
        initViews()
        initData()
    }

    private fun initViews() {
        recycler.layoutManager = LinearLayoutManager(this)
        btn_add.setOnClickListener(this)
        btn_remove.setOnClickListener(this)
    }

    private val mDataAdapter: DataAdapter
        get() {
            return DataAdapter(getDataList())
        }

    private fun initData() {
        val mergeAdapter = MergeAdapter()
        mergeAdapter.addAdapter(HeaderAdapter(getHeaderList()))
        mergeAdapter.addAdapter(mDataAdapter)
        mergeAdapter.addAdapter(FooterAdapter(getFooterList()))
        recycler.adapter = mergeAdapter
    }

    private fun getDataList(): MutableList<String> {
        val list: MutableList<String> = ArrayList()
        for (index in 1..2) {
            list.add("Data")
        }
        return list
    }

    private fun getFooterList(): MutableList<String> {
        val list: MutableList<String> = ArrayList()
        for (index in 1..5) {
            list.add("Footer $index")
        }
        return list
    }

    private fun getHeaderList(): MutableList<String> {
        val list: MutableList<String> = ArrayList()
        for (index in 1..5) {
            list.add("Header $index")
        }
        return list
    }

    override fun onSetupToolbar(toolbar: Toolbar?, actionBar: ActionBar?) {
        ToolBarCommonHolder(this, toolbar, getString(R.string.item_merge_adapter))
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_add -> {
                mDataAdapter.addData(arrayListOf("Data1", "Data2"))
            }
            R.id.btn_remove -> {
                val datas = mDataAdapter.datas
                if (datas.size > 0) {
                    val index = datas.size - 1
                    datas.removeAt(index)
                    mDataAdapter.notifyItemRemoved(index)
                } else {
                    Toast.makeText(this, "没有数据了", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}