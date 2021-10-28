package com.android.exercise.ui.activity.circle;

import android.os.Bundle;
import android.util.Log;
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

//    @DoubleClick
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_reset:
                Log.e("AspectJ", "重置");
                mImageView.setOval(false);
                mImageView.setRadius(0);
                break;
            case R.id.btn_oval:
                Log.e("AspectJ", "设置椭圆");
                mImageView.setOval(true);
                break;
            case R.id.btn_corner:
                Log.e("AspectJ", "设置圆角");
                mImageView.setRadius(300, 300, 0, 0);
                break;
        }
    }
}