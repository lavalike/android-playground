package com.android.exercise.ui.activity;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.android.exercise.R;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.base.manager.AudioManager;
import com.android.exercise.base.toolbar.ToolBarCommonHolder;
import com.android.exercise.ui.widget.RecorderButton;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Recorder
 * Created by wangzhen on 2017/8/8.
 */
public class RecorderActivity extends BaseActivity {

    @BindView(R.id.btn_record)
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

            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onError(String error) {

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
                String path = Environment.getExternalStorageDirectory() + File.separator + "bluelotus.mp3";
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
