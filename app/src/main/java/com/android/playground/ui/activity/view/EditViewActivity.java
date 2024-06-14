package com.android.playground.ui.activity.view;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.android.playground.R;
import com.android.playground.base.BaseActivity;
import com.android.playground.base.toolbar.ToolbarFactory;
import com.android.playground.databinding.ActivityEditViewBinding;
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
