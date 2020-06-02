package com.android.exercise.ui.activity;

import android.graphics.PixelFormat;
import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;

import com.android.exercise.R;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.base.toolbar.ToolBarCommonHolder;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class WindowActivity extends BaseActivity {

    private Button mFloatingButton;
    private WindowManager.LayoutParams mLayoutParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_window);
        ButterKnife.bind(this);
    }

    @Override
    protected void onSetupToolbar(Toolbar toolbar, ActionBar actionBar) {
        new ToolBarCommonHolder(this, toolbar, getString(R.string.item_window));
    }

    @OnClick({R.id.btn_add_win, R.id.btn_remove_win})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add_win:
                add();
                break;
            case R.id.btn_remove_win:
                remove();
                break;
        }
    }

    private void add() {
        mFloatingButton = new Button(this);
        mFloatingButton.setText("FloatingButton");
        mLayoutParams = new WindowManager.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_TOAST,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                PixelFormat.TRANSPARENT
        );
//        mLayoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION;
//        mLayoutParams.flags = WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED;
//        mLayoutParams.format = PixelFormat.TRANSPARENT;
        mLayoutParams.gravity = Gravity.LEFT | Gravity.TOP;
        mLayoutParams.x = 300;
        mLayoutParams.y = 300;
        getWindowManager().addView(mFloatingButton, mLayoutParams);
    }

    private void remove() {
        if (mFloatingButton != null) {
            getWindowManager().removeView(mFloatingButton);
        }
    }
}
