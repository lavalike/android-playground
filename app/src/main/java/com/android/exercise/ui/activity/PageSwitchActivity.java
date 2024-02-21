package com.android.exercise.ui.activity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

import com.android.exercise.base.BaseActivity;
import com.android.exercise.databinding.ActivityPageSwitchBinding;
import com.android.exercise.util.UIUtils;
import com.wangzhen.statusbar.DarkStatusBar;

public class PageSwitchActivity extends BaseActivity {
    private ActivityPageSwitchBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((binding = ActivityPageSwitchBinding.inflate(getLayoutInflater())).getRoot());
        setEvents();
        DarkStatusBar.get().fitDark(this);
    }

    public void setEvents() {
        binding.ivAnim.setOnClickListener(v -> {
            int[] centerXY = getCenterXYInScreen(binding.ivAnim);
            int centerX = centerXY[0];
            int centerY = centerXY[1];
            binding.switchView.setCenterXY(centerX, centerY);
            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            int widthPixels = displayMetrics.widthPixels;
            int heightPixels = displayMetrics.heightPixels;
            double distance = Math.sqrt(Math.pow(Math.abs(widthPixels - centerX), 2) + Math.pow(Math.abs(heightPixels - centerY), 2));
            binding.switchView.setRadiusRange(0, (int) distance);
            binding.switchView.start();
        });
    }

    /**
     * 获取View中心点的屏幕坐标
     *
     * @param view
     * @return 坐标
     */
    public int[] getCenterXYInScreen(View view) {
        int[] array = new int[2];
        view.getLocationOnScreen(array);
        int centerX = array[0] + view.getMeasuredWidth() / 2;
        int centerY = array[1] + view.getMeasuredHeight() / 2;
        int statusBarHeight = UIUtils.getStatusBarHeight(this);
        return new int[]{centerX, centerY - statusBarHeight};
    }
}
