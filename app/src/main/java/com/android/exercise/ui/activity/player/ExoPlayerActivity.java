package com.android.exercise.ui.activity.player;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.android.exercise.R;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.base.toolbar.ToolBarCommonHolder;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackPreparer;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

/**
 * ExoPlayerActivity
 * Created by wangzhen on 2020-01-15.
 */
public class ExoPlayerActivity extends BaseActivity {
    private PlayerView mPlayerView;
    private SimpleExoPlayer mPlayer;
    private long mCurrentPosition = 0L;
    private static final String VIDEO_URL = "http://10.100.119.192:8080/wangzhen/video/beauty.mp4";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exo_player);
        initViews();
        initPlayer();
    }

    private void initPlayer() {
        mPlayer = ExoPlayerFactory.newSimpleInstance(this);
        mPlayerView.setPlayer(mPlayer);
    }

    private void initViews() {
        mPlayerView = (PlayerView) findViewById(R.id.player_view);
        mPlayerView.setPlaybackPreparer(new PlaybackPreparer() {
            @Override
            public void preparePlayback() {
                play();
            }
        });
    }

    @Override
    protected void onSetupToolbar(Toolbar toolbar, ActionBar actionBar) {
        new ToolBarCommonHolder(this, toolbar, "ExoPlayer");
    }

    private void play() {
        play(VIDEO_URL);
    }

    private void play(String url) {
        mPlayer.prepare(buildMediaSource(Uri.parse(url)));
        if (mCurrentPosition >= 0) {
            mPlayer.seekTo(mCurrentPosition);
        }
        mPlayer.setPlayWhenReady(true);
    }

    private MediaSource buildMediaSource(Uri uri) {
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this, "application_name"));
        switch (Util.inferContentType(uri)) {
            case C.TYPE_SS:
                return new SsMediaSource.Factory(dataSourceFactory).createMediaSource(uri);
            case C.TYPE_DASH:
                return new DashMediaSource.Factory(dataSourceFactory).createMediaSource(uri);
            case C.TYPE_HLS:
                return new HlsMediaSource.Factory(dataSourceFactory).createMediaSource(uri);
            default:
                return new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(uri);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPlayer.release();
    }
}
