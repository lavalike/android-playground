package com.android.exercise.ui.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.android.exercise.R;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.base.toolbar.ToolbarFactory;
import com.android.exercise.ui.widget.dialog.PictureWindow;
import com.wangzhen.commons.toolbar.impl.Toolbar;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ADWindowActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adwindow);
        ButterKnife.bind(this);
    }

    @Nullable
    @Override
    public Toolbar createToolbar() {
        return ToolbarFactory.themed(this, getString(R.string.item_ad_window));
    }

    @OnClick(R.id.btn_ad)
    public void onViewClicked() {
        PictureWindow adWindowDialog = new PictureWindow();
        adWindowDialog.showDialog(getSupportFragmentManager());
    }
}
