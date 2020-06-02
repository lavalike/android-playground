package com.android.exercise.ui.activity;

import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import android.view.View;

import com.android.exercise.R;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.base.toolbar.ToolBarCommonHolder;
import com.android.exercise.ui.widget.PasswordView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 自定义EditView
 * Created by wangzhen on 2017/8/15.
 */
public class EditViewActivity extends BaseActivity {

    @BindView(R.id.password_view)
    PasswordView passwordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_view);
        ButterKnife.bind(this);
    }

    @Override
    protected void onSetupToolbar(Toolbar toolbar, ActionBar actionBar) {
        new ToolBarCommonHolder(this, toolbar, getString(R.string.item_edit_view));
    }

    @OnClick(R.id.btn_getpwd)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_getpwd:
                showToast(passwordView.getPassword());
                break;
        }
    }
}
