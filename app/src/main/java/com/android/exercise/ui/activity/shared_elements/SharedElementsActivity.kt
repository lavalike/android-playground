package com.android.exercise.ui.activity.shared_elements

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.exercise.R
import com.android.exercise.base.BaseActivity
import com.android.exercise.base.toolbar.ToolbarFactory
import com.android.exercise.databinding.ActivitySharedElementsBinding
import com.android.exercise.ui.activity.shared_elements.adapter.GridAdapter
import com.android.exercise.ui.activity.shared_elements.entity.GridEntity
import com.wangzhen.commons.toolbar.impl.Toolbar

/**
 * 共享元素
 * Created by wangzhen on 2020/7/20.
 */
class SharedElementsActivity : BaseActivity() {
    private lateinit var binding: ActivitySharedElementsBinding
    private val _spanCount: Int = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySharedElementsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recycler.layoutManager = GridLayoutManager(this, _spanCount)
        binding.recycler.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
            ) {
                super.getItemOffsets(outRect, view, parent, state)
                val divider = view.resources.getDimensionPixelOffset(R.dimen.divider_size)

                outRect.left = divider
                outRect.top = divider
                outRect.right = divider
                outRect.bottom = divider
            }
        })
        binding.recycler.adapter = GridAdapter(list())
    }

    private fun list(): MutableList<GridEntity> {
        val list: MutableList<GridEntity> = ArrayList()
        for (index in 1..20) {
            list.add(GridEntity(R.mipmap.bg_dog, "dog $index"))
        }
        return list
    }

    override fun createToolbar(): Toolbar {
        return ToolbarFactory.themed(this, getString(R.string.item_shared_elements))
    }
}