package com.android.playground.ui.activity.view;

import android.os.Bundle;

import com.android.playground.base.BaseActivity;
import com.android.playground.databinding.ActivitySlidingMenuBinding;

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
