package com.android.exercise.ui.activity.view;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.android.exercise.R;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.base.toolbar.ToolbarFactory;
import com.android.exercise.databinding.ActivityAdwindowBinding;
import com.android.exercise.ui.widget.dialog.PictureWindow;
import com.wangzhen.commons.toolbar.impl.Toolbar;

public class ADWindowActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityAdwindowBinding binding = ActivityAdwindowBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnAd.setOnClickListener(v -> {
            PictureWindow adWindowDialog = new PictureWindow();
            adWindowDialog.showDialog(getSupportFragmentManager());
        });
    }

    @Nullable
    @Override
    public Toolbar createToolbar() {
        return ToolbarFactory.themed(this, getString(R.string.item_ad_window));
    }
}
