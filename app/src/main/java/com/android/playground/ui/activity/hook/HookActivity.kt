package com.android.playground.ui.activity.hook

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.playground.R
import com.android.playground.base.BaseActivity
import com.android.playground.base.toolbar.ToolbarFactory
import com.android.playground.databinding.ActivityHookBinding
import com.wangzhen.adapter.RecyclerAdapter
import com.wangzhen.adapter.callback.OnItemClickCallback
import com.wangzhen.commons.toolbar.impl.Toolbar

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

    override fun createToolbar(): Toolbar {
        return ToolbarFactory.themed(this, getString(R.string.item_hook))
    }
}