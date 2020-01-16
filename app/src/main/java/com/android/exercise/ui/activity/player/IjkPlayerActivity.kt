package com.android.exercise.ui.activity.player

import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.widget.Toolbar
import android.view.View
import com.android.exercise.R
import com.android.exercise.base.BaseActivity
import com.android.exercise.base.toolbar.ToolBarCommonHolder
import com.android.exercise.ui.widget.player.IjkPlayerView

/**
 * ijkPlayer
 * Created by wangzhen on 2020-01-15.
 */
class IjkPlayerActivity : BaseActivity() {
    private val VIDEO_URL = "http://10.100.119.192:8080/wangzhen/video/beauty.mp4"
    private lateinit var mPlayerView: IjkPlayerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ijk_player)
        mPlayerView = findViewById(R.id.player_view) as IjkPlayerView
    }

    override fun onSetupToolbar(toolbar: Toolbar?, actionBar: ActionBar?) {
        ToolBarCommonHolder(this, toolbar, "ijkPlayer")
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.btn_play -> {
                mPlayerView.play(VIDEO_URL)
            }
            R.id.btn_pause -> {
                mPlayerView.pause()
            }
            R.id.btn_resume -> {
                mPlayerView.resume()
            }
            R.id.btn_stop -> {
                mPlayerView.stop()
            }
            R.id.btn_reset -> {
                mPlayerView.reset()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mPlayerView?.release()
    }
}
