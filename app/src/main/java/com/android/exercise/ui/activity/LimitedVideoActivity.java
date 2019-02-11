package com.android.exercise.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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

    //标准预览16:9
    private static final float STANDARD_RATIO = 16f / 9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_limited_video);
        ButterKnife.bind(this);
        optimizeRatio();
        surfaceView.setCallback(this);
    }

    /**
     * 优化显示比例
     */
    private void optimizeRatio() {
        WindowManager manager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        if (manager == null) return;
        Display display = manager.getDefaultDisplay();
        if (display == null) return;
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        int screenWidth = metrics.widthPixels;
        int screenHeight = metrics.heightPixels;
        float realRatio = screenHeight * 1f / screenWidth;
        //屏幕比例大于16:9
        if (realRatio > STANDARD_RATIO) {
            //计算16:9实际对应的高度
            int theoryHeight = (int) (screenWidth * STANDARD_RATIO);
            ViewGroup.LayoutParams layoutParams = surfaceView.getLayoutParams();
            layoutParams.height = theoryHeight;
        }
    }

    @OnClick({R.id.btn_opt, R.id.btn_switch})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_opt:
                if (surfaceView.isRecording()) {
                    btnOpt.setText("开始");
                    surfaceView.stopRecord();
                } else {
                    btnOpt.setText("停止");
                    surfaceView.setMaxSeconds(10);
                    surfaceView.startRecord();
                }
                break;
            case R.id.btn_switch:
                surfaceView.switchCamera();
                break;
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
