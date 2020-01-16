package com.android.exercise.ui.activity.player

import android.net.Uri
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.widget.Toolbar
import android.view.View
import com.android.exercise.R
import com.android.exercise.base.BaseActivity
import com.android.exercise.base.toolbar.ToolBarCommonHolder
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.PlaybackPreparer
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.dash.DashMediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util

/**
 * ExoPlayerActivity
 * Created by wangzhen on 2020-01-15.
 */
class ExoPlayerActivity : BaseActivity() {
    private lateinit var mPlayerView: PlayerView
    private lateinit var mPlayer: SimpleExoPlayer
    private var mCurrentPosition = 0L
    private val VIDEO_URL = "http://10.100.119.192:8080/wangzhen/video/beauty.mp4"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exo_player)
        initViews()
        initPlayer()
    }

    private fun initPlayer() {
        mPlayer = ExoPlayerFactory.newSimpleInstance(this, DefaultTrackSelector())
        mPlayerView.player = mPlayer
    }

    private fun initViews() {
        mPlayerView = findViewById(R.id.player_view) as PlayerView
        mPlayerView.setPlaybackPreparer(PlaybackPreparer {
            play()
        })
    }

    override fun onSetupToolbar(toolbar: Toolbar?, actionBar: ActionBar?) {
        ToolBarCommonHolder(this, toolbar, "ExoPlayer")
    }

    private fun play() {
        play(VIDEO_URL)
    }

    private fun play(url: String) {
        mPlayer.prepare(buildMediaSource(Uri.parse(url)))
        if (mCurrentPosition >= 0) {
            mPlayer.seekTo(mCurrentPosition)
        }
        mPlayer.playWhenReady = true
    }

    private fun buildMediaSource(uri: Uri): MediaSource {
        val dataSourceFactory = DefaultDataSourceFactory(this, Util.getUserAgent(this, "application_name"))
        return when (Util.inferContentType(uri)) {
            C.TYPE_SS -> {
                SsMediaSource.Factory(dataSourceFactory).createMediaSource(uri)
            }
            C.TYPE_DASH -> {
                DashMediaSource.Factory(dataSourceFactory).createMediaSource(uri)
            }
            C.TYPE_HLS -> {
                HlsMediaSource.Factory(dataSourceFactory).createMediaSource(uri)
            }
            else -> {
                ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(uri)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mPlayer.release()
    }
}
