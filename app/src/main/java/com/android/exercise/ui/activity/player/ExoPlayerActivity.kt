package com.android.exercise.ui.activity.player

import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.widget.Toolbar
import com.android.exercise.R
import com.android.exercise.base.BaseActivity
import com.android.exercise.base.toolbar.ToolBarCommonHolder

/**
 * ExoPlayerActivity
 * Created by wangzhen on 2020-01-15.
 */
class ExoPlayerActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exo_player)
    }

    override fun onSetupToolbar(toolbar: Toolbar?, actionBar: ActionBar?) {
        ToolBarCommonHolder(this, toolbar, "ExoPlayer")
    }
}
