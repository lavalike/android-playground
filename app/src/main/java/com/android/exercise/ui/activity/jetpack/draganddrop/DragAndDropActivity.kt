package com.android.exercise.ui.activity.jetpack.draganddrop

import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import com.android.exercise.R
import com.android.exercise.base.BaseActivity
import com.android.exercise.base.toolbar.ToolBarCommonHolder
import com.android.exercise.databinding.ActivityDragAndDropBinding

/**
 * DragAndDropActivity
 * @author: zhen51.wang
 * @date: 2022/10/17/017
 */
class DragAndDropActivity : BaseActivity() {

    private lateinit var binding: ActivityDragAndDropBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDragAndDropBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onSetupToolbar(toolbar: Toolbar?, actionBar: ActionBar?) {
        ToolBarCommonHolder(this, toolbar, getString(R.string.item_drag_and_drop))
    }
}