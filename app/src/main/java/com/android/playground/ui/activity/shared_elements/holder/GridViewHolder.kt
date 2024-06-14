package com.android.playground.ui.activity.shared_elements.holder

import android.app.Activity
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import com.android.playground.R
import com.android.playground.databinding.ItemSharedElementsGridLayoutBinding
import com.android.playground.ui.activity.shared_elements.SharedDetailActivity
import com.android.playground.ui.activity.shared_elements.entity.GridEntity
import com.wangzhen.adapter.base.RecyclerViewHolder

/**
 * GridViewHolder
 * Created by wangzhen on 2020/7/20.
 */
class GridViewHolder(parent: ViewGroup) :
    RecyclerViewHolder<GridEntity>(parent, R.layout.item_shared_elements_grid_layout) {

    override fun bind() {
        val binding = ItemSharedElementsGridLayoutBinding.bind(itemView)
        with(binding) {
            root.setOnClickListener {
                ActivityCompat.startActivity(
                    itemView.context,
                    Intent(itemView.context, SharedDetailActivity::class.java).putExtra(
                        "resId",
                        mData.resId
                    ).putExtra("title", mData.title),
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                        itemView.context as Activity,
                        Pair(tvTitle as View, "grid_title"),
                        Pair(imageView as View, "grid_image")
                    ).toBundle()
                )
            }
            imageView.setImageResource(mData.resId)
            tvTitle.text = mData.title
        }
    }

}