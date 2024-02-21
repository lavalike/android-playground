package com.android.exercise.ui.activity.view;

import android.os.Bundle;

import com.android.exercise.base.BaseActivity;
import com.android.exercise.databinding.ActivitySlidingMenuBinding;

/**
 * 自定义侧滑菜单
 * Created by wangzhen on 2016/11/19
 */
public class SlidingMenuActivity extends BaseActivity {
    private ActivitySlidingMenuBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((binding = ActivitySlidingMenuBinding.inflate(getLayoutInflater())).getRoot());
        setEvents();
    }

    public void setEvents() {
        binding.btnToggleMenu.setOnClickListener(v -> binding.slidingmenu.toggleMenu());
    }
}
