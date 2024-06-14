package com.android.playground.ui.activity.jetpack.draganddrop

import android.content.ClipData
import android.os.Bundle
import android.view.View
import androidx.core.view.DragStartHelper
import androidx.draganddrop.DropHelper
import com.android.playground.R
import com.android.playground.base.BaseActivity
import com.android.playground.base.toolbar.ToolbarFactory
import com.android.playground.databinding.ActivityDragAndDropBinding
import com.wangzhen.commons.toolbar.impl.Toolbar

/**
 * DragAndDropActivity
 * @author: zhen51.wang
 * @date: 2022/10/17/017
 */
class DragAndDropActivity : BaseActivity() {

    companion object {
        const val MIME_TEXT = "text/plain"
        const val MIME_IMAGE = "image/*"
    }

    private lateinit var binding: ActivityDragAndDropBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDragAndDropBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initDragAndDrop()
    }

    private fun initDragAndDrop() {
        with(binding) {
            DragStartHelper(
                tvDragMe
            ) { view, _ ->
                view.startDragAndDrop(
                    ClipData.newPlainText("", tvDragMe.text), View.DragShadowBuilder(view), null, 0
                )
                true
            }.attach()

            DropHelper.configureView(
                this@DragAndDropActivity, containerDrop, arrayOf(MIME_TEXT, MIME_IMAGE)
            ) { _, payload ->
                val clipData = payload.clip
                if (clipData.description.hasMimeType(MIME_TEXT)) {
                    tvDropHere.text = String.format(
                        getString(R.string.drag_and_drop_text), clipData.getItemAt(0).text
                    )
                }
                null
            }

            btnClear.setOnClickListener {
                tvDropHere.text = getString(R.string.drag_and_drop_drop_here)
            }
        }
    }

    override fun createToolbar(): Toolbar {
        return ToolbarFactory.themed(this, getString(R.string.item_drag_and_drop))
    }
}