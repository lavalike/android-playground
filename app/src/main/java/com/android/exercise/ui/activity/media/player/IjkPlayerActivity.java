package com.android.exercise.ui.activity.media.player;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.android.exercise.R;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.base.toolbar.ToolbarFactory;
import com.android.exercise.ui.widget.player.IjkPlayerView;
import com.wangzhen.commons.toolbar.impl.Toolbar;

/**
 * ijkPlayer
 * Created by wangzhen on 2020-01-15.
 */
public class IjkPlayerActivity extends BaseActivity {
    private static final String VIDEO_URL = "http://192.168.10.100:8080/wangzhen/video/beauty.mp4";
    private IjkPlayerView mPlayerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ijk_player);
        mPlayerView = (IjkPlayerView) findViewById(R.id.player_view);
    }

    @Override
    public Toolbar createToolbar() {
        return ToolbarFactory.themed(this, getString(R.string.item_ijkplayer));
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
