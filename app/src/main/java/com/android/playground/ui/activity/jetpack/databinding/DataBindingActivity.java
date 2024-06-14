package com.android.playground.ui.activity.jetpack.databinding;

import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.android.playground.R;
import com.android.playground.base.BaseActivity;
import com.android.playground.databinding.ActivityDataBindingBinding;

public class DataBindingActivity extends BaseActivity {

    private ActivityDataBindingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fitDarkStatus(true);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_data_binding);
        User user = new User();
        user.lastName = "王";
        user.firstName = "震";
        user.age = 20;
        binding.setUser(user);
    }

    public void onClick(View view) {
        User user = new User();
        user.lastName = "张";
        user.firstName = "三";
        user.age = 80;
        binding.setUser(user);
    }
}