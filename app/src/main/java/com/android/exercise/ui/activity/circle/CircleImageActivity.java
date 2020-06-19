package com.android.exercise.ui.activity.circle;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import com.android.exercise.R;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.base.toolbar.ToolBarCommonHolder;

public class CircleImageActivity extends BaseActivity {

    private CircleImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_image);
        mImageView = findViewById(R.id.imageView);
    }

    @Override
    public boolean showToolbar() {
        return false;
    }

    @Override
    protected void onSetupToolbar(Toolbar toolbar, ActionBar actionBar) {
        new ToolBarCommonHolder(this, toolbar, getString(R.string.item_circle_image));
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_reset:
                mImageView.setOval(false);
                mImageView.setCorner(0, 0, 0, 0);
                break;
            case R.id.btn_oval:
                mImageView.setOval(true);
                break;
            case R.id.btn_corner:
                mImageView.setCorner(60, 60, 0, 0);
                break;
        }
    }
}