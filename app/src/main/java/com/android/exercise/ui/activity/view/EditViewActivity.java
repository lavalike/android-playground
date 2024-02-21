package com.android.exercise.ui.activity.view;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.android.exercise.R;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.base.toolbar.ToolbarFactory;
import com.android.exercise.databinding.ActivityEditViewBinding;
import com.wangzhen.commons.toolbar.impl.Toolbar;

/**
 * 自定义EditView
 * Created by wangzhen on 2017/8/15.
 */
public class EditViewActivity extends BaseActivity {

    private ActivityEditViewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((binding = ActivityEditViewBinding.inflate(getLayoutInflater())).getRoot());
        binding.btnGetpwd.setOnClickListener(v -> {
            showToast(binding.passwordView.getPassword());
        });
    }

    @Nullable
    @Override
    public Toolbar createToolbar() {
        return ToolbarFactory.themed(this, getString(R.string.item_edit_view));
    }
}
