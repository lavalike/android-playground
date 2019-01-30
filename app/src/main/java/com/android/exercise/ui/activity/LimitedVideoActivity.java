package com.android.exercise.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.exercise.R;
import com.android.exercise.ui.widget.CameraSurfaceView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * LimitedVideoActivity 受限的视频录制Activity
 * Created by wangzhen on 2019/1/30.
 */
public class LimitedVideoActivity extends AppCompatActivity implements CameraSurfaceView.Callback {

    @BindView(R.id.surface_view)
    CameraSurfaceView surfaceView;
    @BindView(R.id.tv_time_running)
    TextView tvTimeRunning;
    @BindView(R.id.btn_opt)
    Button btnOpt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_limited_video);
        ButterKnife.bind(this);
        surfaceView.setCallback(this);
    }

    @OnClick(R.id.btn_opt)
    public void onViewClicked() {
        if (surfaceView.isRecording()) {
            btnOpt.setText("开始");
            surfaceView.stopRecord();
        } else {
            btnOpt.setText("停止");
            surfaceView.setMaxSeconds(10);
            surfaceView.startRecord();
        }
    }

    @Override
    public void onUpdate(long seconds) {
        tvTimeRunning.setText(seconds + "秒");
    }

    @Override
    public void onSuccess(String videoPath) {
        tvTimeRunning.setText("0秒");
        btnOpt.setText("开始");
        Toast.makeText(this, "录制完成：" + videoPath, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(String error) {
        tvTimeRunning.setText("0秒");
        btnOpt.setText("开始");
        Toast.makeText(this, "录制失败：" + error, Toast.LENGTH_SHORT).show();
    }
}
