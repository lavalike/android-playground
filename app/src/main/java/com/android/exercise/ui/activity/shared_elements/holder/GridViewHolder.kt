package com.android.exercise.ui.activity.shared_elements.holder

import android.app.Activity
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import com.android.exercise.R
import com.android.exercise.ui.activity.shared_elements.SharedDetailActivity
import com.android.exercise.ui.activity.shared_elements.entity.GridEntity
import com.dimeno.adapter.base.RecyclerViewHolder
import kotlinx.android.synthetic.main.item_shared_elements_grid_layout.view.*

/**
 * GridViewHolder
 * Created by wangzhen on 2020/7/20.
 */
class GridViewHolder(parent: ViewGroup) : RecyclerViewHolder<GridEntity>(parent, R.layout.item_shared_elements_grid_layout) {

    override fun bind() {
        itemView.setOnClickListener {
            ActivityCompat.startActivity(
                    itemView.context,
                    Intent(itemView.context, SharedDetailActivity::class.java).putExtra("resId", mData.resId).putExtra("title", mData.title),
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                            itemView.context as Activity,
                            Pair(itemView.tv_title as View, "grid_title"),
                            Pair(itemView.imageView as View, "grid_image")
                    ).toBundle()
            )
        }
        itemView.imageView.setImageResource(mData.resId)
        itemView.tv_title.text = mData.title
    }

}