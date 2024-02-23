package com.android.exercise.ui.activity.media;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.exercise.databinding.ActivityLimitedVideoBinding;
import com.android.exercise.ui.widget.CameraSurfaceView;

/**
 * LimitedVideoActivity 受限的视频录制Activity
 * Created by wangzhen on 2019/1/30.
 */
public class LimitedVideoActivity extends AppCompatActivity implements CameraSurfaceView.Callback {
    private ActivityLimitedVideoBinding binding;

    //标准预览16:9
    private static final float STANDARD_RATIO = 16f / 9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((binding = ActivityLimitedVideoBinding.inflate(getLayoutInflater())).getRoot());
        optimizeRatio();
        setEvents();
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
            ViewGroup.LayoutParams layoutParams = binding.surfaceView.getLayoutParams();
            layoutParams.height = theoryHeight;
        }
    }

    public void setEvents() {
        binding.surfaceView.setCallback(this);
        binding.btnOpt.setOnClickListener(v -> {
            if (binding.surfaceView.isRecording()) {
                binding.btnOpt.setText("开始");
                binding.surfaceView.stopRecord();
            } else {
                binding.btnOpt.setText("停止");
                binding.surfaceView.setMaxSeconds(10);
                binding.surfaceView.startRecord();
            }
        });
        binding.btnSwitch.setOnClickListener(view -> binding.surfaceView.switchCamera());
    }

    @Override
    public void onUpdate(long seconds) {
        binding.tvTimeRunning.setText(seconds + "秒");
    }

    @Override
    public void onSuccess(String videoPath) {
        binding.tvTimeRunning.setText("0秒");
        binding.btnOpt.setText("开始");
        Toast.makeText(this, "录制完成：" + videoPath, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(String error) {
        binding.tvTimeRunning.setText("0秒");
        binding.btnOpt.setText("开始");
        Toast.makeText(this, "录制失败：" + error, Toast.LENGTH_SHORT).show();
    }
}
