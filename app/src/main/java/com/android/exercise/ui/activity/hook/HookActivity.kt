package com.android.exercise.ui.activity.hook

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.exercise.R
import com.android.exercise.base.BaseActivity
import com.android.exercise.base.toolbar.ToolBarCommonHolder
import com.android.exercise.databinding.ActivityHookBinding
import com.dimeno.adapter.RecyclerAdapter
import com.dimeno.adapter.callback.OnItemClickCallback

/**
 * HookActivity
 * Created by wangzhen on 2020/8/6.
 */
class HookActivity : BaseActivity() {
    private lateinit var binding: ActivityHookBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHookBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recycler.layoutManager = LinearLayoutManager(this)
        val adapter = HookAdapter(list())
        adapter.setOnClickCallback { v, pos ->
            Toast.makeText(v.context, "item click $pos", Toast.LENGTH_SHORT).show()
        }
        adapter.addHeader(HookHeader().onCreateView(binding.recycler))
        binding.recycler.adapter = adapter

        hookAdapter(binding.recycler)
    }

    private fun hookAdapter(recycler: RecyclerView) {
        val adapter: RecyclerAdapter<*>
        if (recycler.adapter is RecyclerAdapter<*>) {
            adapter = recycler.adapter as RecyclerAdapter<*>
            val field = RecyclerAdapter::class.java.getDeclaredField("mItemClickCallback")
            field.isAccessible = true
            val callback = field.get(adapter)
            if (callback != null) {
                field.set(adapter, HookItemClickCallback(callback as OnItemClickCallback))
            }
        }
    }

    class HookItemClickCallback(private val callback: OnItemClickCallback) : OnItemClickCallback {
        override fun onItemClick(itemView: View, position: Int) {
            Toast.makeText(itemView.context, "item click hooked!", Toast.LENGTH_SHORT).show()
            callback.onItemClick(itemView, position)
        }
    }

    private fun list(): List<Int> {
        val list = ArrayList<Int>()
        for (i in 1..10)
            list.add(i)
        return list
    }

    override fun onSetupToolbar(toolbar: Toolbar?, actionBar: ActionBar?) {
        ToolBarCommonHolder(this, toolbar, getString(R.string.item_hook))
    }
}