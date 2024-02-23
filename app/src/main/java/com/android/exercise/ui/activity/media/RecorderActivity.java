package com.android.exercise.ui.activity.media;

import android.os.Bundle;
import android.widget.Toast;

import com.android.exercise.R;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.base.manager.AudioManager;
import com.android.exercise.base.toolbar.ToolbarFactory;
import com.android.exercise.databinding.ActivityRecorderBinding;
import com.wangzhen.commons.toolbar.impl.Toolbar;

/**
 * Recorder
 * Created by wangzhen on 2017/8/8.
 */
public class RecorderActivity extends BaseActivity {

    private ActivityRecorderBinding binding;
    private AudioManager mAudioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((binding = ActivityRecorderBinding.inflate(getLayoutInflater())).getRoot());
        init();
        setEvents();
    }

    private void init() {
        binding.btnSystem.setRecorderListener(path -> Toast.makeText(mContext, "文件地址：" + path, Toast.LENGTH_SHORT).show());
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
    public Toolbar createToolbar() {
        return ToolbarFactory.themed(this, getString(R.string.item_recorder));
    }

    public void setEvents() {
        binding.btnPlay.setOnClickListener(v -> {
            String path = "http://10.100.119.192:8080/wangzhen/audio/hby.mp3";
            mAudioManager.startPlay(path);
        });
        binding.btnPause.setOnClickListener(v -> mAudioManager.pausePlay());
        binding.btnResume.setOnClickListener(v -> mAudioManager.resumePlay());
        binding.btnStop.setOnClickListener(v -> mAudioManager.stopPlay());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mAudioManager != null) {
            mAudioManager.onDestroy();
        }
    }
}
