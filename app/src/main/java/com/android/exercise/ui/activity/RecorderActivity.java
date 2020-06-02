package com.android.exercise.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import com.android.exercise.R;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.base.manager.AudioManager;
import com.android.exercise.base.toolbar.ToolBarCommonHolder;
import com.android.exercise.ui.widget.RecorderButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Recorder
 * Created by wangzhen on 2017/8/8.
 */
public class RecorderActivity extends BaseActivity {

    @BindView(R.id.btn_system)
    RecorderButton btnRecord;
    private AudioManager mAudioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recorder);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        btnRecord.setRecorderListener(new RecorderButton.IRecorderListener() {
            @Override
            public void onFinish(String path) {
                Toast.makeText(mContext, "文件地址：" + path, Toast.LENGTH_SHORT).show();
            }
        });
        mAudioManager = AudioManager.get();
        mAudioManager.setPlayStateListener(new AudioManager.AudioPlayStateListener() {
            @Override
            public void onPrepared() {
                showToast("开始播放");
            }

            @Override
            public void onComplete() {
                showToast("播放完毕");
            }

            @Override
            public void onError(String error) {
                showToast("播放出错：" + error);
            }
        });
    }

    @Override
    protected void onSetupToolbar(Toolbar toolbar, ActionBar actionBar) {
        new ToolBarCommonHolder(this, toolbar, getString(R.string.item_recorder));
    }

    @OnClick({R.id.btn_play, R.id.btn_pause, R.id.btn_resume, R.id.btn_stop})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_play:
                String path = "http://10.100.119.192:8080/wangzhen/audio/hby.mp3";
                mAudioManager.startPlay(path);
                break;
            case R.id.btn_pause:
                mAudioManager.pausePlay();
                break;
            case R.id.btn_resume:
                mAudioManager.resumePlay();
                break;
            case R.id.btn_stop:
                mAudioManager.stopPlay();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mAudioManager != null) {
            mAudioManager.onDestroy();
        }
    }
}
