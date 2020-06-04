package com.android.exercise.ui.activity.jetpack.databinding;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;

import com.android.exercise.R;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.databinding.ActivityDataBindingBinding;

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

    @Override
    public boolean showToolbar() {
        return false;
    }

    public void onClick(View view) {
        User user = new User();
        user.lastName = "张";
        user.firstName = "三";
        user.age = 80;
        binding.setUser(user);
    }
}