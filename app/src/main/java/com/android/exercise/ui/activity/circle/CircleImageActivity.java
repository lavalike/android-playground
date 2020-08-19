package com.android.exercise.ui.activity.circle;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import com.android.aspectj.annotation.DoubleClick;
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

        findViewById(R.id.btn_corner).setOnClickListener(new View.OnClickListener() {
            @DoubleClick
            @Override
            public void onClick(View v) {
                Log.e("AspectJ", "设置圆角");
                mImageView.setRadius(300, 300, 0, 0);
            }
        });
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
                mImageView.setRadius(0);
                break;
            case R.id.btn_oval:
                mImageView.setOval(true);
                break;
        }
    }
}