package com.android.exercise.ui.activity.android13

import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import com.android.exercise.R
import com.android.exercise.base.BaseActivity
import com.android.exercise.base.toolbar.ToolBarCommonHolder
import com.android.exercise.databinding.ActivityPostNotificationBinding

/**
 * PostNotificationActivity
 * @author: zhen51.wang
 * @date: 2022/10/19/019
 */
class PostNotificationActivity : BaseActivity() {
    private lateinit var binding: ActivityPostNotificationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onSetupToolbar(toolbar: Toolbar?, actionBar: ActionBar?) {
        ToolBarCommonHolder(this, toolbar, getString(R.string.item_android13_post_notification))
    }
}