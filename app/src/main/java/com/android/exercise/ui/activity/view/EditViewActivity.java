package com.android.exercise.ui.activity.view;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.android.exercise.R;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.base.toolbar.ToolbarFactory;
import com.android.exercise.ui.widget.PasswordView;
import com.wangzhen.commons.toolbar.impl.Toolbar;

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

    @Nullable
    @Override
    public Toolbar createToolbar() {
        return ToolbarFactory.themed(this, getString(R.string.item_edit_view));
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
