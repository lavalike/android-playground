package com.android.exercise.ui.activity.player;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import com.android.exercise.R;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.base.toolbar.ToolBarCommonHolder;
import com.android.exercise.ui.widget.player.IjkPlayerView;

/**
 * ijkPlayer
 * Created by wangzhen on 2020-01-15.
 */
public class IjkPlayerActivity extends BaseActivity {
    private static final String VIDEO_URL = "http://10.100.119.192:8080/wangzhen/video/beauty.mp4";
    private IjkPlayerView mPlayerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ijk_player);
        mPlayerView = (IjkPlayerView) findViewById(R.id.player_view);
    }

    @Override
    protected void onSetupToolbar(Toolbar toolbar, ActionBar actionBar) {
        new ToolBarCommonHolder(this, toolbar, "ijkPlayer");
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_play:
                mPlayerView.play(VIDEO_URL);
            case R.id.btn_pause:
                mPlayerView.pause();
            case R.id.btn_resume:
                mPlayerView.resume();
            case R.id.btn_stop:
                mPlayerView.stop();
            case R.id.btn_reset:
                mPlayerView.reset();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPlayerView.release();
    }
}
